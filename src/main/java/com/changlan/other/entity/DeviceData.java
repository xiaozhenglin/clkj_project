package com.changlan.other.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DeviceData implements Serializable {
	@Id
	private Integer id;	
	private Integer channelSettings_id; //一个设备 其中的一个通道
	private Float amplitude; //幅值
	private Integer frequency; //频次
	private Integer energy; //总能量
	private Integer alarm_amplitude; //幅值报警
	private Integer alarm_amplitude_frequency; //报警幅值频次
	private Date createtime; //创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChannelSettings_id() {
		return channelSettings_id;
	}
	public void setChannelSettings_id(Integer channelSettings_id) {
		this.channelSettings_id = channelSettings_id;
	}
	public Float getAmplitude() {
		return amplitude;
	}
	public void setAmplitude(Float amplitude) {
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
	public Integer getAlarm_amplitude() {
		return alarm_amplitude;
	}
	public void setAlarm_amplitude(Integer alarm_amplitude) {
		this.alarm_amplitude = alarm_amplitude;
	}
	public Integer getAlarm_amplitude_frequency() {
		return alarm_amplitude_frequency;
	}
	public void setAlarm_amplitude_frequency(Integer alarm_amplitude_frequency) {
		this.alarm_amplitude_frequency = alarm_amplitude_frequency;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
	
}
