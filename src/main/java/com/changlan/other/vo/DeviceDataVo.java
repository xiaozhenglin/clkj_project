package com.changlan.other.vo;

import java.util.List;

import com.changlan.other.entity.DeviceData;

public class DeviceDataVo {
	
	

	private int channelSettings_id;
	private List<DeviceData> table;

	public DeviceDataVo(int parseInt, List<DeviceData> table) {
		this.channelSettings_id = parseInt;
		this.table = table;
	}

	
	public DeviceDataVo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getChannelSettings_id() {
		return channelSettings_id;
	}

	public void setChannelSettings_id(int channelSettings_id) {
		this.channelSettings_id = channelSettings_id;
	}

	public List<DeviceData> getTable() {
		return table;
	}

	public void setTable(List<DeviceData> table) {
		this.table = table;
	}	
	

}
