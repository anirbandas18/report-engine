package com.sss.engine.core;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sss.engine.dto.ApplicationCLIOptions;

@Service
public class ApplicationArgsValidator implements Validator {

	@Autowired
	private ApplicationCLIOptions options;
	@Resource(name = "profilePropertyAliases")
	private List<String> profilePropertyAliases;
	
	private ApplicationArguments args;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ApplicationArguments.class.equals(clazz);
	}

	private Boolean validateAliases(String key, Errors errors) {
		List<String> userDefined = args.getOptionValues(key);
		Boolean status = true;
		if(userDefined != null && !userDefined.isEmpty()) {
			status = profilePropertyAliases.containsAll(userDefined);
			if(!status) {
				errors.reject("invalid." + key, key + " name(s) doesn't match defined mappings for tags representing profile properties");
			}
		}
		return status;
	}
	
	private Boolean validateInput(Errors errors) {
		String optionName = options.getInput();
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
	
	private Boolean validateOutput(Errors errors) {
		String optionName = options.getOutput();
		List<String> values = args.getOptionValues(optionName);
		Boolean flag = false;
		if(values != null && values.size() > 1) {
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
			errors.reject("no.args", "Error: No arguments specified!\n" + options.getApplicationHelpMessage());
		} else {
			if(!validateInput(errors)) {
				// log error
			} 
			if(!validateOutput(errors)) {
				// log error
			} 
			if(!validateAliases(options.getFilters(), errors)) {
				// log error
			}
			if(!validateAliases(options.getSupplements(), errors)) {
				// log error
			}
		} 
	}

}
