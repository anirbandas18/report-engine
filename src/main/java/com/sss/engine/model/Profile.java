package com.sss.engine.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sss.engine.core.tags.ProfilePropertyType;

public class Profile {

	private String name;
	private Map<ProfilePropertyType,List<ProfileProperty>> properties;
	public Map<ProfilePropertyType, List<ProfileProperty>> getProperties() {
		return properties;
	}
	public void setProperties(Map<ProfilePropertyType, List<ProfileProperty>> properties) {
		this.properties = properties;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		Profile other = (Profile) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Profile(String name) {
		super();
		this.name = name;
	}
	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		String json = "{name : " + name + ", properties : [%s]" ;
		String propertyStats = "";
		for(ProfilePropertyType key : properties.keySet()) {
			List<ProfileProperty> value = properties.get(key);
			propertyStats = propertyStats + "{" + key.name() + " : " +  value.size() + "}, ";
		}
		propertyStats = propertyStats.trim();
		propertyStats = propertyStats.substring(0, propertyStats.lastIndexOf(','));
		return String.format(json, propertyStats);
	}
	
	public void addProperty(ProfilePropertyType key, ProfileProperty item) {
		List<ProfileProperty> value = this.properties.get(key);
		if(value == null) {
			value = new LinkedList<>();
		}
		value.add(item);
	}
	
	public void addProperties(ProfilePropertyType key, List<ProfileProperty> item) {
		List<ProfileProperty> value = this.properties.get(key);
		if(value == null) {
			value = new LinkedList<>();
		}
		value.addAll(item);
	}
}
