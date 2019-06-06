package com.changlan.other.vo;

import java.util.List;

import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.SimpleEntity;

//第三方数据库数据
public class DeviceDataVo {

	private Integer channelSettings_id; //通道主键id
	private Integer channel_number; //通道号
	private List<DeviceData> table;//设备数据

	public DeviceDataVo(int channelSettings_id, List<DeviceData> table) {
		this.channelSettings_id = channelSettings_id;
		this.table = table;
	}

	
	public DeviceDataVo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DeviceDataVo(SimpleEntity simple, List<DeviceData> table) {
		this.channelSettings_id = simple.getId();
		this.channel_number = simple.getChannel_number();
		//计算值
		for(int i =0;i<table.size();i++) {
			table.get(i).setCaculate();
		}
		this.table = table;
	}


	public Integer getChannelSettings_id() {
		return channelSettings_id;
	}

	public void setChannelSettings_id(Integer channelSettings_id) {
		this.channelSettings_id = channelSettings_id;
	}

	public List<DeviceData> getTable() {
		return table;
	}

	public void setTable(List<DeviceData> table) {
		this.table = table;
	}


	public Integer getChannel_number() {
		return channel_number;
	}


	public void setChannel_number(Integer channel_number) {
		this.channel_number = channel_number;
	}	
	

}
