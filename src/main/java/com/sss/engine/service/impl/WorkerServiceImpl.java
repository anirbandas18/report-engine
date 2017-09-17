package com.sss.engine.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sss.engine.dto.ReportMetadata;
import com.sss.engine.service.FileSystemService;
import com.sss.engine.service.UtilityService;
import com.sss.engine.service.WorkerService;

@Component
public class WorkerServiceImpl implements WorkerService {
	
	@Autowired
	private UtilityService util;
	@Autowired
	private FileSystemService fileSys;
	

	@Override
	public Set<String> parseAndLoadDataSet(ReportMetadata metadata) throws Exception {
		List<String> fileLocations = fileSys.readFilesFromDirectory(metadata.getInputLocation());
		Set<String> filters = metadata.getPropertyFilters();
		List<Future<Set<String>>> parseJobs = new ArrayList<>();
		Set<String> modelProperties = new TreeSet<>();
		for (String fl : fileLocations) {
			// execute thread per xml file for parsing
			Future<Set<String>> job = util.parseXML(fl, filters);
			parseJobs.add(job);
		}
		parseJobs.forEach(job -> {
			try {
				Set<String> result = job.get();
				modelProperties.addAll(result);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		});
		return modelProperties;
	}

	@Override
	public List<String> processAndDumpDataSet(ReportMetadata metadata) throws Exception {
		// TODO Auto-generated method stub
		Set<String> filteredAliases = metadata.getPropertyFilters();
		List<Future<String>> generationJobs = new ArrayList<>();
		List<String> reportPaths = new ArrayList<>();
		for(String alias : filteredAliases) {
			/*List<ProfileProperty> distinctModelProperties = repository.fetchAllDistinctProfilePropertiesOfType(alias);
			System.out.println(alias + " : " + distinctModelProperties);*/
			Future<String> job = util.generateCSV(alias);
			generationJobs.add(job);
		}
		generationJobs.forEach(job -> {
			try {
				String path = job.get();
				reportPaths.add(path);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		});
		return reportPaths;
	}

}
