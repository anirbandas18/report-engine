package com.sss.engine.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.model.Profile;
import com.sss.engine.model.ProfileProperty;
import com.sss.engine.repository.ProfileRepository;
import com.sss.engine.service.UtilityService;

@Component
public class ProfileRepositoryImpl implements ProfileRepository {
	
	@Autowired
	private Map<String,Profile> profileCache;
	@Autowired
	private UtilityService service;

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
		Profile profile = profileCache.get(name);
		return profile;
	}
	
	private Comparator<Profile> profileComparator = new Comparator<Profile>() {
		
		@Override
		public int compare(Profile o1, Profile o2) {
			// TODO Auto-generated method stub
			return o1.getName().compareTo(o2.getName());
		}
	};

	@Override
	public List<String> fetchAllProfileNames() {
		// TODO Auto-generated method stub
		Set<String> set = profileCache.keySet();
		List<String> list = new ArrayList<>(set);
		return list;
	}

	@Override
	public List<ProfileProperty> fetchAllDistinctProfilePropertiesOfType(ProfilePropertyType type) {
		// TODO Auto-generated method stub
		Collection<Profile> profileList = profileCache.values();
		Set<ProfileProperty> properties = new TreeSet<>();
		for(Profile profile : profileList) {
			List<ProfileProperty> value = profile.getProperties(type);
			properties.addAll(value);
		}
		List<ProfileProperty> distinctProperties = new ArrayList<ProfileProperty>(properties); 
		return distinctProperties;
	}

	@Override
	public List<ProfileProperty> fetchAllDistinctProfilePropertiesOfType(String alias) {
		// TODO Auto-generated method stub
		ProfilePropertyType  type = service.getEnumForStringAlias(alias);
		List<ProfileProperty> distinctProperties = fetchAllDistinctProfilePropertiesOfType(type);
		return distinctProperties;
	}

	@Override
	public ProfileProperty fetchValueOfProfilePropertyFromProfile(String profileName, ProfileProperty key) {
		// TODO Auto-generated method stub
		Profile profile = profileCache.get(profileName);
		String alias = key.getAlias();
		//Class<? extends ProfileProperty> profilePropertyImplClass = key.getClass();
		//ProfilePropertyAlias alias = profilePropertyImplClass.getDeclaredAnnotation(ProfilePropertyAlias.class);
		List<ProfileProperty> profileProperties = profile.getProperties(service.getEnumForStringAlias(alias));
		Collections.sort(profileProperties, profilePropertyComparator);
		int index = Collections.binarySearch(profileProperties, key, profilePropertyComparator);
		ProfileProperty item = index >= 0 ? profileProperties.get(index) : null;
		return item;
	}
	
	private Comparator<ProfileProperty> profilePropertyComparator = new Comparator<ProfileProperty>() {

		@Override
		public int compare(ProfileProperty o1, ProfileProperty o2) {
			// TODO Auto-generated method stub
			return o1.getProfilePropertyKey().compareTo(o2.getProfilePropertyKey());
		}
		
	};

}
