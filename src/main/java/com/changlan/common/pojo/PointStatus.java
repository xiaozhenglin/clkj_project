package com.changlan.common.pojo;

public enum PointStatus {
	CONNECT("连接"),OUT_CONNECT("未连接"),DATA_CAN_IN("数据可达"),DATA_CAN_NOT_IN("数据不可达");
	private String name;

	private PointStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
