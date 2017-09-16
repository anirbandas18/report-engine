package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyType;

@ProfilePropertyAlias(name = ProfilePropertyType.USER_PERMISSION)
public class UserPermission implements ProfileProperty, Comparable<UserPermission>{

	@Override
	public int compareTo(UserPermission o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.getName());
	}
	private String name;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
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
	public Object getProperty() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
