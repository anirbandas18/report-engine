package com.sss.engine.model;

import com.sss.engine.core.tags.ProfileField;

@ProfileField(name = "fieldpermissions")
public class FieldPermission implements Comparable<FieldPermission>{

	private Boolean editable;
	private String field;
	private Boolean readable;
	@Override
	public int compareTo(FieldPermission o) {
		// TODO Auto-generated method stub
		return this.field.compareTo(o.getField());
	}
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Boolean getReadable() {
		return readable;
	}
	public void setReadable(Boolean readable) {
		this.readable = readable;
	}
	
	
}
