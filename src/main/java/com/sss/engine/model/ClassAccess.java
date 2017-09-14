package com.sss.engine.model;

public class ClassAccess implements Comparable<ClassAccess> {
	
	private String apexClass;
	private Boolean enabled;
	
	@Override
	public int compareTo(ClassAccess o) {
		// TODO Auto-generated method stub
		return this.apexClass.compareTo(o.getApexClass());
	}

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
	
}
