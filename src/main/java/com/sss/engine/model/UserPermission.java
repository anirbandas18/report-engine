package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.core.tags.ProfilePropertySerializableField;

@ProfilePropertyAlias(name = ProfilePropertyType.USER_PERMISSION)
public class UserPermission implements ProfileProperty/*, Comparable<UserPermission>*/{

	@ProfilePropertyKey
	private String name;
	@ProfilePropertySerializableField
	private Boolean enabled;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		UserPermission other = (UserPermission) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public int compareTo(ProfileProperty o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.getProfilePropertyKey().toString());
	}

}
