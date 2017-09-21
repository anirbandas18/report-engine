package com.sss.engine.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.model.ProfileProperty;

@Service
public interface UtilityService {
	
	public Class<? extends ProfileProperty> searchClassByTag(List<Class<? extends ProfileProperty>> list, String tagName);
	
	@Async
	public Future<Set<String>> parseXML(String xmlFileLocation, Set<String> processableTags) throws Exception;
	
	@Async
	public Future<String> generateFilteredProfilePropertiesCSV(String csvDumpLocation, String fileNamePrefix, String alias) throws IOException;

	@Async(value = "applicationSubThreadPool")
	public Future<List<String>> generateSupplementaryProfilePropertiesCSVs(String csvDumpLocation, String fileNamePrefix, String alias) throws Exception;
	
	public ProfilePropertyType getEnumForStringAlias(final String alias);

}
