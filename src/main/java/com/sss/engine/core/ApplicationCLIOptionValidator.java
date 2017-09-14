package com.sss.engine.core;

import java.util.List;

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
	private ApplicationArguments args;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ApplicationArguments.class.equals(clazz);
	}

	private Boolean validateOption(String optionName, Errors errors) {
		List<String> values = args.getOptionValues(optionName);
		Boolean flag = false;
		if(values == null) {
			errors.reject("no." + optionName, "--" + optionName + " option is mandatory");
		} else if(values.isEmpty()) {
			errors.reject("no." + optionName + ".value", "--" + optionName + " option should have exactly one value");
		} else if(values.size() > 1) {
			errors.reject("multiple." + optionName + ".value", "Error: Too many values found! --" + optionName + " option should have exactly one value");
		} else {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		args = (ApplicationArguments) target;
		if(args.getSourceArgs().length == 0) {
			errors.reject("no.args", "Error: No arguments specified! " + options.getApplicationHelpMessage());
		} else {
			if(!validateOption(options.getInput(), errors)) {
				// log error
			} 
			if(!validateOption(options.getOutput(), errors)) {
				// log error
			}
		} 
	}

}
