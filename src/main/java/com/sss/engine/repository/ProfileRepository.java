package com.sss.engine.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.model.Profile;
import com.sss.engine.model.ProfileProperty;

@Repository
public interface ProfileRepository {
	
	public Boolean storeProfile(String name, Profile profile);
	
	public Profile fetchProfile(String name);
	
	public ProfileProperty fetchValueOfProfilePropertyFromProfile(String profileName, ProfileProperty key);
	
	public List<String> fetchAllProfileNames();
	
	public List<ProfileProperty> fetchAllDistinctProfilePropertiesOfType(ProfilePropertyType type);

	public List<ProfileProperty> fetchAllDistinctProfilePropertiesOfType(String alias);
	
	/*public List<ProfileProperty> fetchAllSubPropertiesOfProfilePropertyType(ProfilePropertyType type);
	
	public List<ProfileProperty> fetchAllSubPropertiesOfProfilePropertyType(String alias);*/
	

}
