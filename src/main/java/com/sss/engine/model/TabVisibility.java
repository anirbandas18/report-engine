package com.sss.engine.model;

import com.sss.engine.core.tags.ProfileField;

@ProfileField(name = "tabvisibilities")
public class TabVisibility implements Comparable<TabVisibility>{

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
	
}
