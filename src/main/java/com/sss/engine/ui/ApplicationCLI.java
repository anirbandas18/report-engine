package com.sss.engine.ui;

import java.util.List;
import java.util.Set;
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
import com.sss.engine.service.WorkerService;

@Configuration
public class ApplicationCLI implements ApplicationRunner, ExitCodeGenerator {

	@Autowired
	private WorkerService worker;
	@Autowired
	private ApplicationArgsValidator validator;
	@Autowired
	private ApplicationArgsToMetadataConverter converter;
	@Autowired
	private ApplicationCLIOptions options;
	
	@Value("0")
	private Integer successCode;
	@Value("2")
	private Integer errorCode;
	private BindingResult errors;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		if (args.containsOption(options.getHelp())) {
			System.out.println(options.getApplicationHelpMessage());
		} else {
			errors = new DirectFieldBindingResult(args, "application CLI options");
			validator.validate(args, errors);
			if (errors.hasErrors()) {
				List<ObjectError> globalErrors = errors.getGlobalErrors();
				String message = globalErrors.stream().map(ObjectError::getDefaultMessage)
						.collect(Collectors.joining(","));
				System.out.println(message);
			} else {
				ReportMetadata reportMetadata = converter.convert(args);
				System.out.println(reportMetadata);
				Long start = System.currentTimeMillis();
				Set<String> modelPropertiyAliases = worker.parseAndLoadDataSet(reportMetadata);
				Long end = System.currentTimeMillis();
				System.out.println("{processingType : xmlParsing, timeTakenInMs : " + (end - start) + "}");
				/*if(reportMetadata.getPropertyFilters().isEmpty()) {
					reportMetadata.setPropertyFilters(modelPropertiyAliases);
				}*/
				Set<String> propertyFilters = reportMetadata.getPropertyFilters();
				propertyFilters.retainAll(modelPropertiyAliases);
				reportMetadata.setPropertyFilters(propertyFilters);
				System.out.println("{distinctPropertyAliases : " + modelPropertiyAliases + "}");
				start = System.currentTimeMillis();
				Integer noOfReports = worker.processAndDumpDataSet(reportMetadata);
				end = System.currentTimeMillis();
				System.out.println("{dumpLocation : " + reportMetadata.getOutputLocation() + ", noOfReports : " + noOfReports + "}");
				System.out.println("{processingType : csvGeneration, timeTakenInMs : " + (end - start) + "}");
			}
		}
	}

	@Override
	public int getExitCode() {
		// TODO Auto-generated method stub
		return errors.hasErrors() ? errorCode : successCode;
	}

}
