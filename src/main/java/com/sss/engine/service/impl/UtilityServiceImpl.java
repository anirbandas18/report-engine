package com.sss.engine.service.impl;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertySerializableField;
import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.dto.FileWrapper;
import com.sss.engine.model.Profile;
import com.sss.engine.model.ProfileProperty;
import com.sss.engine.repository.ProfileRepository;
import com.sss.engine.service.FileSystemService;
import com.sss.engine.service.UtilityService;

@Component
public class UtilityServiceImpl implements UtilityService {
	
	@Autowired
	private ProfileRepository repository;
	@Autowired
	private XMLInputFactory xmlInputFactory;
	@Autowired
	private FileSystemService fileSys; // for file name
	@Resource(name = "applicationModelClasses")
	private List<Class<? extends ProfileProperty>> applicationModelClasses;
	@Resource(name = "parentModelClass")
	private Class<?> parentModelClass;
	
	@Value("#{'${application.model.skip.properties}'.split(',')}")
	private List<String> skipppedEntities;
	@Value("${csv.report.header.prefix}")
	private String csvReportHeaderPrefix;
	@Value(",")
	private String csvDelimitter;
	@Value(".csv")
	private String csvFileExtension;
	
	@Override
	public Class<? extends ProfileProperty> searchClassByTag(List<Class<? extends ProfileProperty>> list, String tagName) {
		// TODO Auto-generated method stub
		Integer low = 0;
		Integer high = list.size() - 1;
		tagName = tagName.toLowerCase();
		Class<? extends ProfileProperty> item = null;
		while (low <= high) {
			Integer mid = (high + low) / 2;
			Class<? extends ProfileProperty> midElement = list.get(mid);
			ProfilePropertyAlias annotation = midElement.getDeclaredAnnotation(ProfilePropertyAlias.class);
			ProfilePropertyType type = annotation.name();
			String aliasName = type.getValue();
			if (aliasName.compareTo(tagName) == 0) {
				item = midElement;
				break;
			} else if (aliasName.compareTo(tagName) > 0) {
				high = mid - 1;
			} else if (aliasName.compareTo(tagName) < 0) {
				low = mid + 1;
			}
		}
		return item;
	}
	
	private Class<?> validateTag(Set<String> processableTags, String tagName) {
		tagName = tagName.toLowerCase();
		String parentModelClassName = parentModelClass.getSimpleName();
		if(tagName.equalsIgnoreCase(parentModelClassName)) {
			return parentModelClass;
		} else if(skipppedEntities.contains(tagName)) {
			return null;
		} else {
			if(processableTags.contains(tagName) || processableTags.isEmpty()) {
				Class<? extends ProfileProperty> clazz = searchClassByTag(applicationModelClasses, tagName);
				return clazz;
			} else { 
				return null;
			}
		}
	}

	@SuppressWarnings("unused")
	public Future<Set<String>> parseXML(String fileLocation, Set<String> processableTags) throws Exception  {
		String tagName = "";
		String tagContent = "";
		boolean currentTagBelongsToModel = false;
		boolean chosenTag = false;
		Object parentModel = null;// profile
		Object currentModelProperty = null; // class implementing ProfileProperty
		Class<?> currentModelPropertyClass = null;
		Field currentModelPropertyField = null;
		InputStream stream = new FileInputStream(fileLocation);
		XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(stream);
		Set<String> modelProperties = new TreeSet<>();
		while (xmlReader.hasNext()) {
			switch (xmlReader.getEventType()) {
			case XMLStreamReader.START_ELEMENT:
				tagName = xmlReader.getLocalName();
				Class<?> tagClass = validateTag(processableTags, tagName);
				if(tagClass != null && tagClass.equals(parentModelClass)) {
					String fileName = fileSys.getFileNameFromPath(fileLocation);
					List<Field> fields = new ArrayList<>(Arrays.asList(parentModelClass.getDeclaredFields()));
					Field f = fields.stream().filter(x -> x.isAnnotationPresent(ProfilePropertyKey.class)).findAny().orElse(null);
					parentModel = parentModelClass.newInstance();
					if(f != null) {
						f.setAccessible(true);
						f.set(parentModel, fileName);
						f.setAccessible(false);
					}
				} else if(tagClass != null) {
					currentModelPropertyClass = tagClass;
					currentModelProperty = currentModelPropertyClass.newInstance();
					modelProperties.add(tagName.toLowerCase());
				} else {// check if tag is field of current model property
					if(currentModelProperty != null) {
						try {
							currentModelPropertyField = currentModelPropertyClass.getDeclaredField(tagName);
							currentTagBelongsToModel = true;
						} catch (NoSuchFieldException e) {
							// swallow exception for skipping tag
							Boolean found = false;
							List<Field> fields = new ArrayList<>(Arrays.asList(currentModelPropertyClass.getDeclaredFields()));
							for(Field f : fields) {
								if(f.isAnnotationPresent(ProfilePropertySerializableField.class)) {
									ProfilePropertySerializableField ann = f.getDeclaredAnnotation(ProfilePropertySerializableField.class);
									if(ann.name().equalsIgnoreCase(tagName)) {
										found = true;
										currentModelPropertyField = f;
										break;
									}
								}
							}
							currentTagBelongsToModel = found;
						}
					}
				}
				break;
			case XMLStreamReader.END_ELEMENT:
				tagName = xmlReader.getLocalName();
				tagClass = validateTag(processableTags, tagName);
				if(tagClass != null && tagClass.equals(parentModelClass)) {
					// save parentModel to cache
					String processor = Thread.currentThread().getName();
					Profile profile = (Profile) parentModel;
					Boolean status = repository.storeProfile(profile.getName(), profile);
					System.out.println("{profileName : " + profile.getName() + ", storeStatus : " + status + ", profileProcessor : " + processor + "}");
				} else if(tagClass != null) {
					Method addProperty = parentModelClass.getDeclaredMethod("addProperty", ProfilePropertyType.class, ProfileProperty.class);
					ProfilePropertyType key = getEnumForStringAlias(tagName.toLowerCase());
					if(key != null) {
						addProperty.invoke(parentModel, key, (ProfileProperty) currentModelProperty);
					}
				} else {
					currentTagBelongsToModel = false;
				}
				break;
			case XMLStreamReader.CHARACTERS:
				tagContent = xmlReader.getText();
				if(currentTagBelongsToModel) {
					currentModelPropertyField.setAccessible(true);
					if(currentModelPropertyField.getType() == Boolean.class) {
						currentModelPropertyField.set(currentModelProperty, Boolean.valueOf(tagContent));
					} else {
						currentModelPropertyField.set(currentModelProperty, tagContent);
					}
					currentModelPropertyField.setAccessible(false);
				}
				break;
			}
			xmlReader.next();
		}
		return new AsyncResult<Set<String>>(modelProperties);
	}

	@Override
	public Future<String> generateFilteredProfilePropertiesCSV(String csvDumpLocation, String fileNamePrefix, String alias) throws  IOException {
		// TODO Auto-generated method stub
		String processorName = Thread.currentThread().getName();
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		List<ProfileProperty> allProperties = repository.fetchAllDistinctProfilePropertiesOfType(alias);
		String header = createHeader(allProperties);
		bw.write(header);
		bw.newLine();
		
		// logic to write report body
		
		for(String profileName : repository.fetchAllProfileNames()) {
			Profile profile = repository.fetchProfile(profileName);
			ProfilePropertyType key = getEnumForStringAlias(alias);
			List<ProfileProperty> specificProperties = profile.getProperties(key);
			Integer count = 0;
			String line = profileName + csvDelimitter;
			for(ProfileProperty parent : allProperties) {
				Map<String,String> parentFields = parent.formatSerilizableFields();
				String blankTemplate = "-";// parentFields.size() > 1 ? "-:" : "-";
				String blankCell = String.join("", Collections.nCopies(parentFields.size(), blankTemplate));
				if(count < specificProperties.size()) {
					ProfileProperty child = specificProperties.get(count);
					if(parent.equals(child)) {
						Map<String,String> childFields = child.formatSerilizableFields();
						String formattedValues = formatDataCollection(childFields.values());
						line = line + formattedValues;
						count++;
					} else {
						line = line + blankCell;
					}
					line = line + csvDelimitter;
				} else {
					line = line + blankCell + csvDelimitter;
				}
			}
			bw.write(line);
			bw.newLine();
		}
		
		bw.flush();
		String report = sw.toString();
		String name = fileNamePrefix + "_" + alias + csvFileExtension;
		FileWrapper fw = new FileWrapper();
		fw.setContent(report.getBytes());
		fw.setName(name);
		String reportLocation = fileSys.writeFileToDirectory(csvDumpLocation, fw);
		String reportName = fileSys.getFileNameFromPath(reportLocation);
		System.out.println("{reportName : " + reportName + ", reportSize : " + report.length() + ", reportProcessor : " + processorName + "}");
		return new AsyncResult<String>(reportLocation);
	}
	
	private String createHeader(List<ProfileProperty> profileProperties) {
		String header = csvReportHeaderPrefix + csvDelimitter;
		for(ProfileProperty property : profileProperties) {
			Map<String,String> serializableFields = property.formatSerilizableFields();
			String key = property.getProfilePropertyKey();
			String formattedKeys = formatDataCollection(serializableFields.keySet());
			String columnName = key + " - " + formattedKeys;
			header = header + columnName + csvDelimitter;
		}
		header = header.substring(0, header.lastIndexOf(','));
		return header;
	}
	
	private String formatDataCollection(Collection<String> data) {
		String formatted = data.toString();
		formatted = formatted.replaceAll(",\\s", "");// :
		formatted = formatted.substring(1, formatted.length() - 1);
		return formatted;
	}
	
	@Override
	public ProfilePropertyType getEnumForStringAlias(final String alias) {
		// TODO Auto-generated method stub
		List<ProfilePropertyType> profilePropertyTypes = new ArrayList<>(Arrays.asList(ProfilePropertyType.values()));
		ProfilePropertyType key = profilePropertyTypes.stream().filter(ppty -> ppty.getValue().equalsIgnoreCase(alias)).findAny().orElse(null);
		return key;
	}

	@Override
	public Future<List<String>> generateSupplementaryProfilePropertiesCSVs(String csvDumpLocation, String fileNamePrefix, String alias)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProfileProperty> allProperties = repository.fetchAllDistinctProfilePropertiesOfType(alias);
		List<String> reportNames = new ArrayList<>();
		Map<String,Future<String>> generationJobs = new TreeMap<>();
		for(ProfileProperty property : allProperties) {
			String fileNameDistinguisher = alias + "_" + property.getProfilePropertyKey();
			String fileName = fileNamePrefix + "_" + fileNameDistinguisher + csvFileExtension;
			FileWrapper fw = new FileWrapper();
			fw.setName(fileName);
			Future<String> job = generateIndividualCSV(csvDumpLocation, alias, fw, property);
			generationJobs.put(fileNameDistinguisher, job);
		}
		for(String key : generationJobs.keySet()) {
			Future<String> value = generationJobs.get(key);
			String reportFileLocation = value.get();
			if(StringUtils.hasText(reportFileLocation)) {
				reportNames.add(fileSys.getFileNameFromPath(reportFileLocation));
			}
		}
		return new AsyncResult<>(reportNames);
	}
	
	@Async("applicationSubThreadPool")
	public Future<String> generateIndividualCSV(String csvDumpLocation, String alias, FileWrapper fw, ProfileProperty property) throws IOException {
		String processorName = Thread.currentThread().getName();
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		String header = createHeader(Arrays.asList(property));
		bw.write(header);
		bw.newLine();
		for(String profileName : repository.fetchAllProfileNames()) {
			String line = profileName + csvDelimitter;
			ProfileProperty item = repository.fetchValueOfProfilePropertyFromProfile(profileName, property);
			if(item == null) {
				Map<String,String> parentFields = property.formatSerilizableFields();
				String blankTemplate = "-";// parentFields.size() > 1 ? "-:" : "-";
				String blankCell = String.join("", Collections.nCopies(parentFields.size(), blankTemplate));
				line = line + blankCell;
			} else {
				Map<String,String> childFields = item.formatSerilizableFields();
				String formattedValues = formatDataCollection(childFields.values());
				line = line + formattedValues;
			}
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		String report = sw.toString();
		fw.setContent(report.getBytes());
		String dirLocation = csvDumpLocation + FileSystems.getDefault().getSeparator() + alias;
		String reportLocation = fileSys.writeFileToDirectory(dirLocation, fw);
		String reportName = fileSys.getFileNameFromPath(reportLocation);
		System.out.println("{reportName : " + reportName + ", reportSize : " + report.length() + ", reportProcessor : " + processorName + "}");
		return new AsyncResult<String>(reportLocation);
	}


}
