package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyKey;
import com.sss.engine.core.tags.ProfilePropertyType;
import com.sss.engine.core.tags.ProfilePropertySerializableField;

@ProfilePropertyAlias(name = ProfilePropertyType.RECORD_TYPE_VISIBILITY)
public class RecordTypeVisibility implements ProfileProperty/*, Comparable<RecordTypeVisibility>*/{

	/*@Override
	public int compareTo(RecordTypeVisibility o) {
		// TODO Auto-generated method stub
		return this.recordType.compareTo(o.getRecordType());
	}*/
	@ProfilePropertySerializableField(name = "default")
	private Boolean default_;
	@ProfilePropertySerializableField
	private Boolean visible;
	@ProfilePropertyKey
	private String recordType;
	public Boolean getDefault_() {
		return default_;
	}
	public void setDefault_(Boolean default_) {
		this.default_ = default_;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
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
		result = prime * result + ((recordType == null) ? 0 : recordType.hashCode());
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
		RecordTypeVisibility other = (RecordTypeVisibility) obj;
		if (recordType == null) {
			if (other.recordType != null)
				return false;
		} else if (!recordType.equals(other.recordType))
			return false;
		return true;
	}
	@Override
	public int compareTo(ProfileProperty o) {
		// TODO Auto-generated method stub
		return this.recordType.compareTo(o.getProfilePropertyKey());
	}
	
}
