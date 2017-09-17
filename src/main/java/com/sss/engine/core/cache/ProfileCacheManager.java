package com.sss.engine.core.cache;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.model.Profile;

@Repository
public interface ProfileCacheManager {
	
	public Boolean storeProfile(String name, Profile profile);
	
	public Profile fetchProfile(String name);
	
	public List<String> fetchAllProfileNames();
	
	public List<String> fetchAllDistinctProfilePropertiesOfType(ProfilePropertyType type);

	public List<String> fetchAllDistinctProfilePropertiesOfType(String alias);

}
