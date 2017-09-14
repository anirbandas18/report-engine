package com.sss.engine.dto;

import java.util.Set;

import org.springframework.stereotype.Component;


@Component
public class ReportMetadata {
	
	private Set<String> filters;
	private String inputLocation;
	private String outputLocation;
	public Set<String> getFilters() {
		return filters;
	}
	public void setFilters(Set<String> filters) {
		this.filters = filters;
	}
	public String getInputLocation() {
		return inputLocation;
	}
	public void setInputLocation(String inputLocation) {
		this.inputLocation = inputLocation;
	}
	public String getOutputLocation() {
		return outputLocation;
	}
	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}
	
	

}
