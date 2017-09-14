package com.sss.engine.model;

import java.util.List;

public class Profile {
	
	private List<ClassAccess> classAccesses;
	private List<FieldPermission> fieldPermissions;
	private List<LayoutAssignment> layoutAssignments;
	private List<ObjectPermission> objectPermissions;
	private List<PageAccess> pageAccesses;
	private List<RecordTypeVisibility> recordTypeVisibilities;
	private List<TabVisibility> tabVisibilities;
	private List<UserPermission> userPermissions;
	public List<ClassAccess> getClassAccesses() {
		return classAccesses;
	}
	public void setClassAccesses(List<ClassAccess> classAccesses) {
		this.classAccesses = classAccesses;
	}
	public List<FieldPermission> getFieldPermissions() {
		return fieldPermissions;
	}
	public void setFieldPermissions(List<FieldPermission> fieldPermissions) {
		this.fieldPermissions = fieldPermissions;
	}
	public List<LayoutAssignment> getLayoutAssignments() {
		return layoutAssignments;
	}
	public void setLayoutAssignments(List<LayoutAssignment> layoutAssignments) {
		this.layoutAssignments = layoutAssignments;
	}
	public List<ObjectPermission> getObjectPermissions() {
		return objectPermissions;
	}
	public void setObjectPermissions(List<ObjectPermission> objectPermissions) {
		this.objectPermissions = objectPermissions;
	}
	public List<PageAccess> getPageAccesses() {
		return pageAccesses;
	}
	public void setPageAccesses(List<PageAccess> pageAccesses) {
		this.pageAccesses = pageAccesses;
	}
	public List<RecordTypeVisibility> getRecordTypeVisibilities() {
		return recordTypeVisibilities;
	}
	public void setRecordTypeVisibilities(List<RecordTypeVisibility> recordTypeVisibilities) {
		this.recordTypeVisibilities = recordTypeVisibilities;
	}
	public List<TabVisibility> getTabVisibilities() {
		return tabVisibilities;
	}
	public void setTabVisibilities(List<TabVisibility> tabVisibilities) {
		this.tabVisibilities = tabVisibilities;
	}
	public List<UserPermission> getUserPermissions() {
		return userPermissions;
	}
	public void setUserPermissions(List<UserPermission> userPermissions) {
		this.userPermissions = userPermissions;
	}
	
	

}
