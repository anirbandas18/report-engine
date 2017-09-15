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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apexPage == null) ? 0 : apexPage.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageAccess other = (PageAccess) obj;
		if (apexPage == null) {
			if (other.apexPage != null)
				return false;
		} else if (!apexPage.equals(other.apexPage))
			return false;
		return true;
	}
	
	
	
}
