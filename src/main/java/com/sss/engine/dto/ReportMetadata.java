package com.sss.engine.dto;

import java.util.Set;

import org.springframework.stereotype.Component;


@Component
public class ReportMetadata {
	
	private Set<String> filter;
	private String reportDumpLocation;
	private ProfileMetadata profileMetadata;
	public String getReportDumpLocation() {
		return reportDumpLocation;
	}
	public void setReportDumpLocation(String reportDumpLocation) {
		this.reportDumpLocation = reportDumpLocation;
	}
	public ProfileMetadata getProfileMetadata() {
		return profileMetadata;
	}
	public void setProfileMetadata(ProfileMetadata profileMetadata) {
		this.profileMetadata = profileMetadata;
	}
	
	public Set<String> getFilter() {
		return filter;
	}
	public void setFilter(Set<String> filter) {
		this.filter = filter;
	}
	

}
