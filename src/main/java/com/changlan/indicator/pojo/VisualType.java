package com.changlan.indicator.pojo;

public enum VisualType {
	NUMBER("数值") , STATE("状态") , CONTROLL("控制"); 
	private String name;

	private VisualType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
