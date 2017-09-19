package com.sss.engine.core;

import java.nio.file.FileSystems;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sss.engine.dto.ApplicationCLIOptions;
import com.sss.engine.dto.ReportMetadata;
import com.sss.engine.service.FileSystemService;

@Service
public class ApplicationArgsToMetadataConverter implements Converter<ApplicationArguments, ReportMetadata> {

	@Autowired
	private ApplicationCLIOptions options;
	@Autowired
	private FileSystemService fileSys;
	
	@Value("#{systemProperties['user.dir']}")
	private String currentWorkingDirectory;
	@Value("${default.report.dump.location.directory}")
	private String defaultDumpLocationDirectory;
	
	
	@Override
	public ReportMetadata convert(ApplicationArguments source) {
		// TODO Auto-generated method stub
		String input = source.getOptionValues(options.getInput()).get(0);
		String output = "";
		if(source.containsOption(options.getOutput())) {
			output = source.getOptionValues(options.getOutput()).get(0);
		} else {
			output = currentWorkingDirectory + FileSystems.getDefault().getSeparator() + defaultDumpLocationDirectory;
		}
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
