package com.sss.engine.entity.cpk;

public class ClassAccessPK implements ProfileFieldPK {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6565908950436774215L;
	private String apexClass;
	public String getApexClass() {
		return apexClass;
	}
	public void setApexClass(String apexClass) {
		this.apexClass = apexClass;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apexClass == null) ? 0 : apexClass.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
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
		ClassAccessPK other = (ClassAccessPK) obj;
		if (apexClass == null) {
			if (other.apexClass != null)
				return false;
		} else if (!apexClass.equals(other.apexClass))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "{profile=" + profile + ", apexClass=" + apexClass + "}";
	}
	
	private String profile;

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}
