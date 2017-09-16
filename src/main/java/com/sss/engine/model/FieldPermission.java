package com.sss.engine.model;


public class FieldPermission implements ProfileProperty, Comparable<FieldPermission>{

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((editable == null) ? 0 : editable.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldPermission other = (FieldPermission) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		return true;
	}
	@Override
	public Object getProperty() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
