package com.sss.engine.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.josql.QueryParseException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.model.ProfileProperty;

@Service
public interface UtilityService {
	
	public Class<? extends ProfileProperty> searchClassByTag(List<Class<? extends ProfileProperty>> list, String tagName);
	
	@Async
	public Future<Set<String>> parseXML(String xmlFileLocation, Set<String> filters) throws Exception;
	
	@Async
	public Future<String> generateCSV(String modelPropertyAlias) throws QueryParseException;
	
	public ProfilePropertyType getEnumForStringAlias(final String alias);

}
