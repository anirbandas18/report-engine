package com.sss.engine.model;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ProfileProperty extends Serializable{
	
	default String toJSON() {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(getProperty());
			return json;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object getProperty();
	
}
