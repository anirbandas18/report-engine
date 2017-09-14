package com.sss.engine.model;

public class RecordTypeVisibility implements Comparable<RecordTypeVisibility>{

	@Override
	public int compareTo(RecordTypeVisibility o) {
		// TODO Auto-generated method stub
		return this.recordType.compareTo(o.getRecordType());
	}
	
	private Boolean default_;
	private Boolean visible;
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

	
}
