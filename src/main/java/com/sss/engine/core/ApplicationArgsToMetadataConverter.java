package com.sss.engine.core;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.sss.engine.dto.ApplicationCLIOptions;
import com.sss.engine.dto.ReportMetadata;
import com.sss.engine.service.FileSystemService;

@Service
public class ApplicationArgsToMetadataConverter implements Converter<ApplicationArguments, ReportMetadata> {

	@Autowired
	private ApplicationCLIOptions options;
	@Autowired
	private FileSystemService fileSys;
	
	@Override
	public ReportMetadata convert(ApplicationArguments source) {
		// TODO Auto-generated method stub
		String input = source.getOptionValues(options.getInput()).get(0);
		String output = source.getOptionValues(options.getOutput()).get(0);
		List<String> f = source.getOptionValues(options.getFilters());
		Set<String> filters = new LinkedHashSet<>();
		if(f != null) {
			for(String x : f) {
				filters.add(x.toLowerCase());
			}
		}
		String prefix = fileSys.getLastSegmentFromPath(input);
		ReportMetadata target = new ReportMetadata();
		target.setPropertyFilters(filters);
		target.setInputLocation(input);
		target.setOutputLocation(output);
		target.setReportNamePrefix(prefix);
		return target;
	}

}
