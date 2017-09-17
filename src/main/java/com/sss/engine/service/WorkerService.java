package com.sss.engine.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sss.engine.dto.ReportMetadata;

@Service
public interface WorkerService {
	
	public Set<String> parseAndLoadDataSet(ReportMetadata metadata) throws Exception;
	
	public List<String> processAndDumpDataSet(ReportMetadata metadata) throws Exception;

}
