package com.changlan.user.pojo;

public enum OperationDataType {
	
	UNAUTHORITY("越权"),
	EXCEPTION("异常"),
	SUCCESS("成功");
	
	private String name;

	private OperationDataType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
