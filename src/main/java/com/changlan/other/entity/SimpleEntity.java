package com.changlan.other.entity;

import javax.persistence.Entity;

@Entity
public class SimpleEntity {
	
	@javax.persistence.Id
	private String Id;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
	
}
