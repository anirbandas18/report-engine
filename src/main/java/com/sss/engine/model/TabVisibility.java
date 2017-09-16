package com.sss.engine.model;

public class TabVisibility implements ProfileProperty, Comparable<TabVisibility>{

	@Override
	public int compareTo(TabVisibility o) {
		// TODO Auto-generated method stub
		return this.tab.compareTo(o.getTab());
	}
	private String tab;
	private String visibility;
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tab == null) ? 0 : tab.hashCode());
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
		TabVisibility other = (TabVisibility) obj;
		if (tab == null) {
			if (other.tab != null)
				return false;
		} else if (!tab.equals(other.tab))
			return false;
		return true;
	}
	@Override
	public Object getProperty() {
		// TODO Auto-generated method stub
		return this;
	}
	
	
}
