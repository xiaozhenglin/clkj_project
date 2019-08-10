package com.changlan.point.pojo;

public enum LanguageType {
	CHINESE("中文"),
	ENGLISH("英语");
	
	private String name;

	
	private LanguageType(String name) {
		this.name = name;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

		
	
}
