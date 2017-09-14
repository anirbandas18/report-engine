package com.sss.engine.model;

public class ObjectPermission implements Comparable<ObjectPermission>{

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
	
	

}
