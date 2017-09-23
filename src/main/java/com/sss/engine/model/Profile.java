package com.sss.engine.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertyType;

public class Profile {

	@ProfilePropertyKey
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
		this.properties = new LinkedHashMap<>();
	}
	public Profile() {
		super();
		// TODO Auto-generated constructor stub
		this.properties = new LinkedHashMap<>();
	}
	@Override
	public String toString() {
		String json = "{name : " + name + ", properties : [" ;
		String propertyStats = "";
		for(ProfilePropertyType key : properties.keySet()) {
			List<ProfileProperty> value = properties.get(key);
			propertyStats = propertyStats + "{" + key.getValue() + " : " +  value.size() + "}, ";
		}
		if(!properties.isEmpty()) {
			propertyStats = propertyStats.trim();
			propertyStats = propertyStats.substring(0, propertyStats.lastIndexOf(','));
		}
		json = json.concat(propertyStats).concat("]}");
		return json;
	}
	
	public void addProperty(ProfilePropertyType key, ProfileProperty item) {
		List<ProfileProperty> value = this.properties.get(key);
		if(value == null) {
			value = new LinkedList<>();
		}
		value.add(item);
		this.properties.put(key, value);
	}
	
	public void addProperties(ProfilePropertyType key, Collection<ProfileProperty> item) {
		List<ProfileProperty> value = this.properties.get(key);
		if(value == null) {
			value = new LinkedList<>();
		}
		value.addAll(item);
		this.properties.put(key, value);
	}
	
	public List<ProfileProperty> getProperties(ProfilePropertyType key) {
		List<ProfileProperty> value = this.properties.get(key);
		if(value == null) {
			value = new LinkedList<>();
		}
		return value;
	}
}
