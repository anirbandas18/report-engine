package com.sss.engine.dto;

import java.util.Set;

import org.springframework.stereotype.Component;


@Component
public class ReportMetadata {
	
	private Set<String> propertyFilters;
	private Set<String> supplementaryProperties;
	private String inputLocation;
	private String outputLocation;
	private String reportNamePrefix;
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
	public Set<String> getPropertyFilters() {
		return propertyFilters;
	}
	public void setPropertyFilters(Set<String> propertyFilters) {
		this.propertyFilters = propertyFilters;
	}
	public String getReportNamePrefix() {
		return reportNamePrefix;
	}
	public void setReportNamePrefix(String reportNamePrefix) {
		this.reportNamePrefix = reportNamePrefix;
	}
	public Set<String> getSupplementaryProperties() {
		return supplementaryProperties;
	}
	public void setSupplementaryProperties(Set<String> supplementaryProperties) {
		this.supplementaryProperties = supplementaryProperties;
	}
	@Override
	public String toString() {
		return "{propertyFilters=" + propertyFilters + ", supplementaryProperties=" + supplementaryProperties
				+ ", inputLocation=" + inputLocation + ", outputLocation=" + outputLocation + ", reportNamePrefix="
				+ reportNamePrefix + "}";
	}
	
	

}
