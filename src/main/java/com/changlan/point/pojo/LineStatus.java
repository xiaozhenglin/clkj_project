package com.changlan.point.pojo;

public enum LineStatus {
	ON_LINE(0,"在线"),OUT_LINE(1,"不在线"),PRE_ALRAM(2,"预警"),EMERGECY_ALARM(3,"紧急报警");
	private Integer id;
	private String name;
	
	
	private LineStatus(Integer id,String name) {
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
