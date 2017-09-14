package com.sss.engine.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCLIOptions {
	
	@Value("${application.help.message}")
	private String applicationHelpMessage;
	@Value("help")
	private String help;
	@Value("input")
	private String input;
	@Value("output")
	private String output;
	@Value("filter")
	private String filter;
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getApplicationHelpMessage() {
		return applicationHelpMessage;
	}
	public void setApplicationHelpMessage(String applicationHelpMessage) {
		this.applicationHelpMessage = applicationHelpMessage;
	}
	
	

}
