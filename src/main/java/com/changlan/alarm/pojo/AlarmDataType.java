package com.changlan.alarm.pojo;

public enum AlarmDataType {
	UP_LEVER_ELEARLY_ALARM("上限预警"),
	UP_LEVER_ALARM("上限报警"),
	DOWN_LEVER_ELEARLY_ALARM("下限预警"),
	DOWN_LEVER_ALARM("下限预警"),
	EARLY_ALARM("预警"),
	ALARM("报警");
	
	private String name;

	private AlarmDataType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
