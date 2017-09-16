package com.sss.engine.core.cache;

import org.springframework.stereotype.Repository;

import com.sss.engine.model.Profile;

@Repository
public interface ProfileCacheManager {
	
	public Boolean storeProfile(String name, Profile profile);
	
	public Profile fetchProfile(String name);

}
