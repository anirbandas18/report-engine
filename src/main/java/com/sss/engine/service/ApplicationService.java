package com.sss.engine.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.sss.engine.model.ProfileProperty;

@Service
public interface ApplicationService {
	
	public Class<? extends ProfileProperty> searchClassByTag(List<Class<? extends ProfileProperty>> list, String tagName);
	
	public Future<Set<String>> parseXML(String xmlFilePath, Set<String> filters) throws Exception;

}
