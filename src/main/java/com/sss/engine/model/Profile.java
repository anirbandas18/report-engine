package com.sss.engine.model;

import java.util.ArrayList;
import java.util.List;

import com.sss.engine.core.tags.Key;

public class Profile {
	
	@Key
	private String name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Profile() {
		this.classAccesses = new ArrayList<>();
		this.fieldPermissions  = new ArrayList<>();
		this.layoutAssignments = new ArrayList<>();
		this.objectPermissions = new ArrayList<>();
		this.pageAccesses = new ArrayList<>();
		this.recordTypeVisibilities = new ArrayList<>();
		this.tabVisibilities = new ArrayList<>();
		this.userPermissions = new ArrayList<>();
	}

	public void addClassAccess(ClassAccess ca) {
		this.classAccesses.add(ca);
	}
	
	public void addFieldPermission(FieldPermission fp) {
		this.fieldPermissions.add(fp);
	}
	
	public void addLayoutAssignment(LayoutAssignment la) {
		this.layoutAssignments.add(la);
	}
	
	public void addObjectPermission(ObjectPermission op) {
		this.objectPermissions.add(op);
	}
	
	public void addPageAccess(PageAccess pa) {
		this.pageAccesses.add(pa);
	}
	
	public void addRecordTypeVisibility(RecordTypeVisibility rtv) {
		this.recordTypeVisibilities.add(rtv);
	}
	
	public void addTabVisibility(TabVisibility tv) {
		this.tabVisibilities.add(tv);
	}
	
	public void addUserPermission(UserPermission up) {
		this.userPermissions.add(up);
	}
	@Override
	public String toString() {
		return "{name=" + name + ", classAccesses=" + classAccesses.size() + ", fieldPermissions=" + fieldPermissions.size()
				+ ", layoutAssignments=" + layoutAssignments.size() + ", objectPermissions=" + objectPermissions.size()
				+ ", pageAccesses=" + pageAccesses.size() + ", recordTypeVisibilities=" + recordTypeVisibilities.size()
				+ ", tabVisibilities=" + tabVisibilities.size() + ", userPermissions=" + userPermissions.size() + "}";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Profile other = (Profile) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
