package com.sss.engine.model;

import com.sss.engine.core.tags.ProfilePropertyAlias;
import com.sss.engine.core.tags.ProfilePropertyType;

@ProfilePropertyAlias(name = ProfilePropertyType.LAYOUT_ASSIGNMENT)
public class LayoutAssignment implements Comparable<LayoutAssignment> {
	
	private String layout;
	private String recordType;
	
	@Override
	public int compareTo(LayoutAssignment o) {
		// TODO Auto-generated method stub
		return this.layout.compareTo(o.getLayout());
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	

}
