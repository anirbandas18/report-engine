package com.sss.engine.model;

import com.sss.engine.core.tags.ProfileField;

@ProfileField(name = "pageaccesses")
public class PageAccess implements Comparable<PageAccess>{

	@Override
	public int compareTo(PageAccess o) {
		// TODO Auto-generated method stub
		return this.apexPage.compareTo(o.getApexPage());
	}
	
	private String apexPage;
	private Boolean enabled;
	public String getApexPage() {
		return apexPage;
	}
	public void setApexPage(String apexPage) {
		this.apexPage = apexPage;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
}
