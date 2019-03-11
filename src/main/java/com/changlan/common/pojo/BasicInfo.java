package com.changlan.common.pojo;

import java.io.Serializable;

public class BasicInfo {
	
	private String id;
	private String name;
	
	
	public BasicInfo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public BasicInfo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	
}
