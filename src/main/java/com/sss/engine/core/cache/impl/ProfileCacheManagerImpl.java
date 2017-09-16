package com.sss.engine.core.cache.impl;

import java.util.Comparator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sss.engine.core.cache.ProfileCacheManager;
import com.sss.engine.model.Profile;

@Component
public class ProfileCacheManagerImpl implements ProfileCacheManager {
	
	@Autowired
	private Map<String,Profile> profileCache;

	@Override
	public Boolean storeProfile(String name, Profile profile) {
		// TODO Auto-generated method stub
		Profile old = profileCache.put(name, profile);
		if(old == null) {
			return true;
		} else {
			int comparison = profileComparator.compare(old, profile);
			return comparison == 0 ? false : true;
		}
	}

	@Override
	public Profile fetchProfile(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Comparator<Profile> profileComparator = new Comparator<Profile>() {
		
		@Override
		public int compare(Profile o1, Profile o2) {
			// TODO Auto-generated method stub
			return o1.getName().compareTo(o2.getName());
		}
	};

}
