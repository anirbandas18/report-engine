package com.sss.engine.core.tags;

public enum ProfilePropertyType {

	CLASS_ACCESS("classaccesses"),
	FIELD_PERMISSION("fieldpermissions"),
	LAYOUT_ASSIGNMENT("layoutassignments"),
	OBJECT_PERMISSION("objectpermissions"),
	PAGE_ACCESS("pageaccesses"),
	RECORD_TYPE_VISIBILITY("recordtypevisibilities"),
	TAB_VISIBILITY("tabvisibilities"),
	USER_PERMISSION("userpermissions");
	
	private String value;
	
	private ProfilePropertyType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
