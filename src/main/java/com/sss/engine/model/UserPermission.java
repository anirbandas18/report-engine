package com.sss.engine.model;

public class UserPermission implements Comparable<UserPermission>{

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

}
