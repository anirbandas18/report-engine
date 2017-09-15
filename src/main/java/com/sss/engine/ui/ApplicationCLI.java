package com.sss.engine.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.ObjectError;

import com.sss.engine.core.ApplicationArgsToMetadataConverter;
import com.sss.engine.core.ApplicationArgsValidator;
import com.sss.engine.dto.ApplicationCLIOptions;
import com.sss.engine.dto.ReportMetadata;
import com.sss.engine.service.ApplicationService;
import com.sss.engine.service.FileSystemService;

@Configuration
public class ApplicationCLI implements ApplicationRunner, ExitCodeGenerator {
	
	@Autowired
	private ApplicationService service;
	@Autowired
	private ApplicationArgsValidator validator;
	@Autowired
	private ApplicationArgsToMetadataConverter converter;
	@Autowired
	private ApplicationCLIOptions options;
	@Autowired
	private FileSystemService fileSys;
	@Value("0")
	private Integer successCode;
	@Value("2")
	private Integer errorCode;
	private BindingResult errors;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		if(args.containsOption(options.getHelp())) {
			System.out.println(options.getApplicationHelpMessage());
		} else {
			errors = new DirectFieldBindingResult(args, "application CLI options");
			validator.validate(args, errors);
			if(errors.hasErrors()) {
				List<ObjectError> globalErrors = errors.getGlobalErrors();
				String message = globalErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
				System.out.println(message);
			} else {
				ReportMetadata reportMetadata = converter.convert(args);
				System.out.println(reportMetadata);
				List<String> fileLocations = fileSys.readFilesFromDirectory(reportMetadata.getInputLocation());
				List<CompletableFuture<Set<String>>> parseJobs = new ArrayList<>();
				for(String fl : fileLocations) {
					// exeute thread per xml file for parsing
					CompletableFuture<Set<String>> job = service.parseXML(fl, reportMetadata.getPropertyFilters());
					parseJobs.add(job);
				}
				CompletableFuture<Void> allJobs = CompletableFuture.allOf(parseJobs.toArray(new CompletableFuture[parseJobs.size()]));
				allJobs.join();
			}
		}
	}

	@Override
	public int getExitCode() {
		// TODO Auto-generated method stub
		return errors.hasErrors() ? errorCode : successCode;
	}

}
