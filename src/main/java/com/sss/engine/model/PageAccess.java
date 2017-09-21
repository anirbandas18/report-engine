package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.core.tags.ProfilePropertySerializableField;

@ProfilePropertyAlias(name = ProfilePropertyType.PAGE_ACCESS)
public class PageAccess implements ProfileProperty/*,Comparable<PageAccess>*/{

	/*@Override
	public int compareTo(PageAccess o) {
		// TODO Auto-generated method stub
		return this.apexPage.compareTo(o.getApexPage());
	}*/
	@ProfilePropertyKey
	private String apexPage;
	@ProfilePropertySerializableField
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
	public ProfileProperty getProperty() {
		// TODO Auto-generated method stub
		return this;
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
	@Override
	public int compareTo(ProfileProperty o) {
		// TODO Auto-generated method stub
		return this.apexPage.compareTo(o.getProfilePropertyKey());
	}
	
}
