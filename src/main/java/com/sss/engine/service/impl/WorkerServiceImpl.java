package com.sss.engine.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
	/*@Autowired
	private TaskExecutor xmlParser;
	@Autowired
	private TaskExecutor filteredReportsGenerator;
	@Autowired
	private TaskExecutor supplementaryReportsGenerator;*/
	

	@Override
	public Set<String> parseAndLoadDataSet(ReportMetadata metadata) throws Exception {
		List<String> fileLocations = fileSys.readFilesFromDirectory(metadata.getInputLocation());
		Set<String> processableTags = new TreeSet<>();
		processableTags.addAll(metadata.getPropertyFilters());
		processableTags.addAll(metadata.getSupplementaryProperties());
		List<Future<Set<String>>> parseJobs = new ArrayList<>();
		Set<String> modelProperties = new TreeSet<>();
		for (String fl : fileLocations) {
			// execute thread per xml file for parsing
			Future<Set<String>> job = util.parseXML(fl, processableTags);
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
		//((ThreadPoolTaskExecutor)xmlParser).shutdown();
		return modelProperties;
	}

	@Override
	public Integer processAndDumpDataSet(ReportMetadata metadata) throws Exception {
		// TODO Auto-generated method stub
		Integer noOfReports = 0;
		List<String> reportLocations = new ArrayList<>();
		Future<List<String>> filteredReportJobs = generateFilteredReports(metadata);
		reportLocations.addAll(filteredReportJobs.get());
		//Future<List<String>> supplementaryReportJobs = generateSupplementaryReports(metadata);
		//reportLocations.addAll(supplementaryReportJobs.get());
		for(String reportFileLocation : reportLocations) {
			if(StringUtils.hasText(reportFileLocation)) {
				noOfReports++;
			}
		}
		return noOfReports;
	}
	
	@Async
	public Future<List<String>> generateFilteredReports(ReportMetadata metadata) throws Exception {
		List<String> filteredReportLocations = new ArrayList<>();
		for(String alias : metadata.getPropertyFilters()) {
			Future<String> job = util.generateFilteredProfilePropertiesCSV(metadata.getOutputLocation(), metadata.getReportNamePrefix(), alias);
			String reportLocation = job.get();
			filteredReportLocations.add(reportLocation);
		}
		//((ThreadPoolTaskExecutor)filteredReportsGenerator).shutdown();
		return new AsyncResult<>(filteredReportLocations);
	}
	
	@Async("applicationSubThreadPool")
	public Future<List<String>> generateSupplementaryReports(ReportMetadata metadata) throws Exception {
		List<String> supplementaryReportsLocation = new ArrayList<>();
		for(String alias : metadata.getSupplementaryProperties()) {
			Future<List<String>> job = util.generateSupplementaryProfilePropertiesCSVs(metadata.getOutputLocation(), metadata.getReportNamePrefix(), alias);
			List<String> reportlocations = job.get();
			supplementaryReportsLocation.addAll(reportlocations);
		}
		//((ThreadPoolTaskExecutor)supplementaryReportsGenerator).shutdown();
		return new AsyncResult<>(supplementaryReportsLocation);
	}

}
