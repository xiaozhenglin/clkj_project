package com.changlan.point.pojo;

public enum DataType {
	HUAN_LIU("环流"),TEMPREATRUE("温度"); 
	String name;

	private DataType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
