package com.sss.engine.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.sss.engine.dto.ReportMetadata;

@Service
public interface WorkerService {
	
	public Set<String> parseAndLoadDataSet(ReportMetadata metadata) throws Exception;
	
	public void processAndDumpDataSet(Set<String> distinctProcessedModelPropertyAliases);

}
