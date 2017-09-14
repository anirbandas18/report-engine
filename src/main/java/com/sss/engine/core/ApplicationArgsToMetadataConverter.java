package com.sss.engine.core;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.sss.engine.dto.ApplicationCLIOptions;
import com.sss.engine.dto.ReportMetadata;

@Service
public class ApplicationArgsToMetadataConverter implements Converter<ApplicationArguments, ReportMetadata> {

	@Autowired
	private ApplicationCLIOptions options;
	
	@Override
	public ReportMetadata convert(ApplicationArguments source) {
		// TODO Auto-generated method stub
		String input = source.getOptionValues(options.getInput()).get(0);
		String output = source.getOptionValues(options.getOutput()).get(0);
		Set<String> filters = new LinkedHashSet<>(source.getOptionValues(options.getFilter()));
		ReportMetadata target = new ReportMetadata();
		target.setFilters(filters);
		target.setInputLocation(input);
		target.setOutputLocation(output);
		return target;
	}

}
