package com.sss.engine.service.impl;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.josql.QueryParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.sss.engine.core.tags.ProfilePropertyAlias;
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
	@Value("${query1}")
	private String josqlQuery1;
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
	
	private Class<?> validateTag(Set<String> filters, String tagName) {
		tagName = tagName.toLowerCase();
		String parentModelClassName = parentModelClass.getSimpleName();
		if(tagName.equalsIgnoreCase(parentModelClassName)) {
			return parentModelClass;
		} else if(skipppedEntities.contains(tagName)) {
			return null;
		} else {
			if(filters.contains(tagName) || filters.isEmpty()) {
				Class<? extends ProfileProperty> clazz = searchClassByTag(applicationModelClasses, tagName);
				return clazz;
			} else { 
				return null;
			}
		}
	}

	@SuppressWarnings("unused")
	public Future<Set<String>> parseXML(String fileLocation, Set<String> filters) throws Exception  {
		String tagName = "";
		String tagContent = "";
		boolean currentTagBelongsToModel = false;
		boolean chosenTag = false;
		Object parentModel = null;// profile
		Object currentModelProperty = null; // class with @ProfileField
		Class<?> currentModelPropertyClass = null;
		Field currentModelPropertyField = null;
		InputStream stream = new FileInputStream(fileLocation);
		XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(stream);
		Set<String> modelProperties = new TreeSet<>();
		while (xmlReader.hasNext()) {
			switch (xmlReader.getEventType()) {
			case XMLStreamReader.START_ELEMENT:
				tagName = xmlReader.getLocalName();
				Class<?> tagClass = validateTag(filters, tagName);
				if(tagClass != null && tagClass.equals(parentModelClass)) {
					String fileName = fileSys.getFileNameFromPath(fileLocation);
					List<Field> fields = new ArrayList<>(Arrays.asList(parentModelClass.getDeclaredFields()));
					Field f = fields.stream().filter(x -> x.isAnnotationPresent(com.sss.engine.core.tags.ProfilePropertyKey.class)).findAny().orElse(null);
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
							currentTagBelongsToModel = false;
						}
					}
				}
				break;
			case XMLStreamReader.END_ELEMENT:
				tagName = xmlReader.getLocalName();
				tagClass = validateTag(filters, tagName);
				if(tagClass != null && tagClass.equals(parentModelClass)) {
					// save parentModel to cache
					String processor = Thread.currentThread().getName();
					Profile profile = (Profile) parentModel;
					Boolean status = repository.storeProfile(profile.getName(), profile);
					System.out.println("{profileName : " + profile.getName() + ", storeStatus : " + status + ", profileProcessor : " + processor + "}");
				} else if(tagClass != null) {
					Method addProperty = parentModelClass.getDeclaredMethod("addProperty", ProfilePropertyType.class, ProfileProperty.class);
					ProfilePropertyType key = getEnumForStringAlias(tagName.toLowerCase());
					addProperty.invoke(parentModel, key, (ProfileProperty) currentModelProperty);
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
	public Future<String> generateCSV(String csvDumpLocation, String fileNamePrefix, String alias) throws QueryParseException, IOException {
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
			Integer count = 0, n = 0;
			String line = profileName + csvDelimitter;
			for(ProfileProperty parent : allProperties) {
				if(count < specificProperties.size()) {
					ProfileProperty child = specificProperties.get(count);
					if(parent.equals(child)) {
						
						count++;
					} else {
						line = line + String.join("", Collections.nCopies(n, "-")) + csvDelimitter;
					}
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
			Map<String,String> serializableFields = property.formatSerilizableFieldKeys();
			String formattedKeys = serializableFields.keySet().toString();
			formattedKeys = formattedKeys.replaceAll(",\\s", "/");
			formattedKeys = formattedKeys.substring(1, formattedKeys.length() - 1);
			String key = property.getProfilePropertyKey();
			String columnName = key + " : " + formattedKeys;
			header = header + columnName + csvDelimitter;
		}
		header = header.substring(0, header.lastIndexOf(','));
		return header;
	}
	
	@Override
	public ProfilePropertyType getEnumForStringAlias(final String alias) {
		// TODO Auto-generated method stub
		List<ProfilePropertyType> profilePropertyTypes = new ArrayList<>(Arrays.asList(ProfilePropertyType.values()));
		ProfilePropertyType key = profilePropertyTypes.stream().filter(ppty -> ppty.getValue().equalsIgnoreCase(alias)).findAny().orElse(null);
		return key;
	}

}
