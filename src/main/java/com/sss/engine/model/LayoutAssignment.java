package com.sss.engine.model;

import java.util.Set;
import java.util.TreeSet;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertySerializableField;
import com.sss.engine.core.tags.ProfilePropertyType;

@ProfilePropertyAlias(name = ProfilePropertyType.LAYOUT_ASSIGNMENT, isMultiValueType = true)
public class LayoutAssignment implements ProfileProperty {
	@ProfilePropertyKey
	private String layout;
	@ProfilePropertySerializableField(name = "recordType")
	private Set<String> recordTypes;

	public LayoutAssignment() {
		this.recordTypes = new TreeSet<>();
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void addRecordType(String recordType) {
		this.recordTypes.add(recordType);
	}

	public Set<String> getRecordTypes() {
		return recordTypes;
	}

	public void setRecordTypes(Set<String> recordTypes) {
		this.recordTypes = recordTypes;
	}

	@Override
	public int compareTo(ProfileProperty o) {
		// TODO Auto-generated method stub
		return this.layout.compareTo(o.getProfilePropertyKey());
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
		result = prime * result + ((layout == null) ? 0 : layout.hashCode());
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
		LayoutAssignment other = (LayoutAssignment) obj;
		if (layout == null) {
			if (other.layout != null)
				return false;
		} else if (!layout.equals(other.layout))
			return false;
		return true;
	}
	
	

}
