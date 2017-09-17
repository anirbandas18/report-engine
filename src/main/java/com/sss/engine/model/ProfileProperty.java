package com.sss.engine.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertySerializableField;

public interface ProfileProperty extends Serializable {

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

	default String getProfilePropertyKey() {
		Class<? extends ProfileProperty> clazz = getProperty().getClass();
		List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
		Field key = fields.stream().filter(f -> f.isAnnotationPresent(ProfilePropertyKey.class)).findAny().orElse(null);
		Field sub = fields.stream().filter(f -> (!f.isAnnotationPresent(ProfilePropertyKey.class)
				&& !f.isAnnotationPresent(ProfilePropertySerializableField.class))).findAny().orElse(null);
		String value = "";
		key.setAccessible(true);
		key = key != null ? key : sub;
		try {
			value = key.get(getProperty()).toString();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		key.setAccessible(false);
		return value;
	}

	default Map<String,String> formatSerilizableFieldKeys() {
		Map<String,String> serializableFields = new LinkedHashMap<>();
		Class<? extends ProfileProperty> clazz = getProperty().getClass();
		for (Field f : clazz.getDeclaredFields()) {
			if (f.isAnnotationPresent(ProfilePropertySerializableField.class)) {
				ProfilePropertySerializableField ann = f.getDeclaredAnnotation(ProfilePropertySerializableField.class);
				String name = StringUtils.hasText(ann.name()) ? ann.name() : f.getName();
				String key = null;
				String value = "-";
				// key processing
				if (ann.isShorthand()) {
					int ascii = name.charAt(0);
					if (ascii >= 97 && ascii <= 122) {
						ascii = ascii - 32;
					}
					key = Character.toString((char)ascii);
				} else {
					key = name;
				}
				// value processing
				f.setAccessible(true);
				try {
					Object val = f.get(getProperty());
					if(f.getType() == Boolean.class) {
						Boolean flag = (Boolean) val;
						if(flag != null && flag == true) {
							if(key.length() != 1) {
								int ascii = key.charAt(0);
								if (ascii >= 97 && ascii <= 122) {
									ascii = ascii - 32;
								}
								value = Character.toString((char)ascii);
							} else {
								value = key;
							}
						} else {
							value = "-";
						} 
					} else {
						if(val.toString().length() != 0) {
							value = ann.isShorthand() ? val.toString().substring(0, 1) : val.toString();
						} else {
							value = "-";
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				f.setAccessible(false);
				serializableFields.put(key, value);
			}
		}
		return serializableFields;
	}

	default String formatSerilizableFieldValues() {
		String formattedFieldValues = "";
		Class<? extends ProfileProperty> clazz = getProperty().getClass();
		for (Field f : clazz.getDeclaredFields()) {
			if (f.isAnnotationPresent(ProfilePropertySerializableField.class)) {
				try {
					if(f.getType() == Boolean.class) {
						
					} else {
						
					}
					f.get(getProperty());
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return formattedFieldValues;
	}

	public ProfileProperty getProperty();

}
