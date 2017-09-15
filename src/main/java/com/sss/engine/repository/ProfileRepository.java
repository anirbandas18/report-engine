package com.sss.engine.repository;

import org.springframework.stereotype.Repository;

import com.sss.engine.model.Profile;

@Repository
public interface ProfileRepository {
	
	public String save(Profile profile);
	
	public Profile findByName(String name);

}
