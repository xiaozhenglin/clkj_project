package com.changlan.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_MONITOR_SYSTEM")
public class TblMonitorSystemEntity {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "MONITOR_ID", unique = true )
	 private Integer monitorId;

	 @Column(name = "NAME", unique = true )
	 private String name;
	 
	// @Column(name = "CHANNEL_ID", unique = true )
	// private Integer channelId;

	public Integer getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(Integer monitorId) {
		this.monitorId = monitorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public Integer getChannelId() { return channelId; }
	 * 
	 * public void setChannelId(Integer channelId) { this.channelId = channelId; }
	 */
	 
	 

}
