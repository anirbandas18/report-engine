package com.sss.engine.core;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
		List<String> f = source.getOptionValues(options.getFilter());
		Set<String> filters = new LinkedHashSet<>(f == null ? new ArrayList<>() : f);
		String prefix = getLastDirectoryName(input);
		ReportMetadata target = new ReportMetadata();
		target.setPropertyFilters(filters);
		target.setInputLocation(input);
		target.setOutputLocation(output);
		target.setReportNamePrefix(prefix);
		return target;
	}

	private String getLastDirectoryName(String input) {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(input);
		String pathSeparator = FileSystems.getDefault().getSeparator();
		String dirLocation = dirPath.toString();
		int beginIndex = dirLocation.lastIndexOf(pathSeparator) + 1;
		String lastDirName = dirLocation.substring(beginIndex);
		return lastDirName;
	}

}
