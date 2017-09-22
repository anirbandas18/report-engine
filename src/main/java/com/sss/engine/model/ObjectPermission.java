package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.core.tags.ProfilePropertySerializableField;

@ProfilePropertyAlias(name = ProfilePropertyType.OBJECT_PERMISSION)
public class ObjectPermission implements ProfileProperty/*, Comparable<ObjectPermission>*/{

	@ProfilePropertySerializableField(name = "create")
	private Boolean allowCreate;
	@ProfilePropertySerializableField(name = "read")
	private Boolean allowRead;
	@ProfilePropertySerializableField(name = "edit")
	private Boolean allowEdit;
	@ProfilePropertySerializableField(name = "delete")
	private Boolean allowDelete;
	@ProfilePropertySerializableField(name = "modifyAll")
	private Boolean modifyAllRecords;
	@ProfilePropertySerializableField(name = "viewAll")
	private Boolean viewAllRecords;
	@ProfilePropertyKey
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
	public ProfileProperty getProperty() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
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
	public int compareTo(ProfileProperty o) {
		// TODO Auto-generated method stub
		return this.object.compareTo(o.getProfilePropertyKey().toString());
	}
	
}
