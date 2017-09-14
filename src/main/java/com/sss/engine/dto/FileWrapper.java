package com.sss.engine.dto;

public class FileWrapper {
	
	private String name;
	private byte[] content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public FileWrapper(String name, byte[] content) {
		super();
		this.name = name;
		this.content = content;
	}
	public FileWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
