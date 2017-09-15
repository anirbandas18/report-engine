package com.sss.engine.model;

import com.sss.engine.core.tags.ProfileField;

@ProfileField(name = "layoutassignments")
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
