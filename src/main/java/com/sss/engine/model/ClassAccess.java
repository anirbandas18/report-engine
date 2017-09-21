package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.core.tags.ProfilePropertySerializableField;

@ProfilePropertyAlias(name = ProfilePropertyType.CLASS_ACCESS)
public class ClassAccess  implements ProfileProperty{
	@ProfilePropertyKey
	private String apexClass;
	@ProfilePropertySerializableField
	private Boolean enabled;
	
	/*@Override
	public int compareTo(ClassAccess o) {
		// TODO Auto-generated method stub
		return this.apexClass.compareTo(o.getApexClass());
	}*/

	public String getApexClass() {
		return apexClass;
	}

	public void setApexClass(String apexClass) {
		this.apexClass = apexClass;
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
		result = prime * result + ((apexClass == null) ? 0 : apexClass.hashCode());
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
		ClassAccess other = (ClassAccess) obj;
		if (apexClass == null) {
			if (other.apexClass != null)
				return false;
		} else if (!apexClass.equals(other.apexClass))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProfileProperty o) {
		// TODO Auto-generated method stub
		return this.apexClass.compareTo(o.getProfilePropertyKey());
	}

}
