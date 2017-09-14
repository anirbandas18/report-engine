package com.sss.engine.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sss.engine.dto.ApplicationCLIOptions;

@Service
public class ApplicationCLIOptionValidator implements Validator {

	@Autowired
	private ApplicationCLIOptions options;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ApplicationArguments.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ApplicationArguments args = (ApplicationArguments) target;
		if(args.getSourceArgs().length == 0) {
			errors.reject("no.args", "Error: No arguments specified! " + options.getApplicationHelpMessage());
		} else if(!args.containsOption(options.getInput()) || args.getOptionValues(options.getInput()).isEmpty()) {
			errors.reject("no.input", "--input option is mandatory");
		} else if(!args.containsOption(options.getOutput()) || args.getOptionValues(options.getOutput()).isEmpty()) {
			errors.reject("no.output", "--output option is mandatory");
		}
	}

}
