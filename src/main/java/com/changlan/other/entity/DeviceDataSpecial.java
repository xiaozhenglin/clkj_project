package com.changlan.other.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="devicedataspecial")
public class DeviceDataSpecial implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;	
	private String record_id; //一个设备 其中的一个通道主键id
	private Float amplitude; //幅值
	private Integer frequency; //频次
	private Integer energy; //总能量
	
	private Date createtime; //创建时间
	private Float phase; // 相位
		
	private String phase_no;  //A,B,C相位
	
	@Column(name="POINT_ID")
	private Integer pointId;
	@Column(name="COMMAND_RECORD_ID")
	private Integer commond_record_id; //
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Float getPhase() {
		return phase;
	}
	public void setPhase(Float phase) {
		this.phase = phase;
	}
	public String getPhase_no() {
		return phase_no;
	}
	public void setPhase_no(String phase_no) {
		this.phase_no = phase_no;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public Integer getCommond_record_id() {
		return commond_record_id;
	}
	public void setCommond_record_id(Integer commond_record_id) {
		this.commond_record_id = commond_record_id;
	}
			
}
