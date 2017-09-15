package com.sss.engine.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

@Service
public interface ApplicationService {
	
	public Class<?> searchClassByTag(List<Class<?>> list, String tagName);
	
	public CompletableFuture<Set<String>> parseXML(String xmlFilePath, Set<String> filters) throws Exception;

}
