package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyType;

@ProfilePropertyAlias(name = ProfilePropertyType.OBJECT_PERMISSION)
public class ObjectPermission implements ProfileProperty, Comparable<ObjectPermission>{

	@Override
	public int compareTo(ObjectPermission o) {
		// TODO Auto-generated method stub
		return this.object.compareTo(o.getObject());
	}
	
	private Boolean allowCreate;
	private Boolean allowRead;
	private Boolean allowEdit;
	private Boolean allowDelete;
	private Boolean modifyAllRecords;
	private Boolean viewAllRecords;
	private String object;
	public Boolean getAllowCreate() {
		return allowCreate;
	}
	public void setAllowCreate(Boolean allowCreate) {
		this.allowCreate = allowCreate;
	}
	public Boolean getAllowRead() {
		return allowRead;
	}
	public void setAllowRead(Boolean allowRead) {
		this.allowRead = allowRead;
	}
	public Boolean getAllowEdit() {
		return allowEdit;
	}
	public void setAllowEdit(Boolean allowEdit) {
		this.allowEdit = allowEdit;
	}
	public Boolean getAllowDelete() {
		return allowDelete;
	}
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public Boolean getModifyAllRecords() {
		return modifyAllRecords;
	}
	public void setModifyAllRecords(Boolean modifyAllRecords) {
		this.modifyAllRecords = modifyAllRecords;
	}
	public Boolean getViewAllRecords() {
		return viewAllRecords;
	}
	public void setViewAllRecords(Boolean viewAllRecords) {
		this.viewAllRecords = viewAllRecords;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		ObjectPermission other = (ObjectPermission) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}
	@Override
	public Object getProperty() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public String toString() {
		return object + " : CREDMV";
	}
	
}
