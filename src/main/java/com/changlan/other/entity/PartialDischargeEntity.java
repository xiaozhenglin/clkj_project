package com.changlan.other.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PartialDischargeEntity {
	
	@Id
	@Column(name= "ROW_ID")
	private Integer rowId;
	private Integer deviceId; //采集器ID
	private Integer channel_number;//通道号
	private String location; //监测位置
	private Integer amplitude; //幅值
	private Integer frequency; //频次
	private Integer energy; //总能量
	private Date updatetime; //更新时间
	private Integer alarm_amplitude_frequency; //报警
	private String location_detail; //监测位置详细信息
	private Integer point_id; //监控点id
	private String  POINT_NAME;//监控点名称
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getChannel_number() {
		return channel_number;
	}
	public void setChannel_number(Integer channel_number) {
		this.channel_number = channel_number;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getAmplitude() {
		return amplitude;
	}
	public void setAmplitude(Integer amplitude) {
		this.amplitude = amplitude;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Integer getEnergy() {
		return energy;
	}
	public void setEnergy(Integer energy) {
		this.energy = energy;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getAlarm_amplitude_frequency() {
		return alarm_amplitude_frequency;
	}
	public void setAlarm_amplitude_frequency(Integer alarm_amplitude_frequency) {
		this.alarm_amplitude_frequency = alarm_amplitude_frequency;
	}
	public String getLocation_detail() {
		return location_detail;
	}
	public void setLocation_detail(String location_detail) {
		this.location_detail = location_detail;
	}
	public Integer getPoint_id() {
		return point_id;
	}
	public void setPoint_id(Integer point_id) {
		this.point_id = point_id;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public String getPOINT_NAME() {
		return POINT_NAME;
	}
	public void setPOINT_NAME(String pOINT_NAME) {
		POINT_NAME = pOINT_NAME;
	}
	
	
	
}
