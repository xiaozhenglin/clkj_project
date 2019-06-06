package com.changlan.other.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SimpleEntity {
	
	@javax.persistence.Id
	private Integer Id;
	
	@Column(name="channel_number")
	private Integer channel_number;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getChannel_number() {
		return channel_number;
	}

	public void setChannel_number(Integer channel_number) {
		this.channel_number = channel_number;
	}
	
	
}
