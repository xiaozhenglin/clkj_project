package com.changlan.point.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;


import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class AppCountEntity {
	@Id
	private Integer ROW_ID;
	
    private Integer alarm_total = 0;	
	@Column(name = "alarm_deal")
	private Integer alarm_deal = 0 ;
	private Integer alarm_not_deal= 0 ;
	
	private Integer point_total = 0;
	private Integer point_online = 0;
	private Integer point_not_online = 0;
		
	
	
	private Integer huanliu_points_total;
	private Integer jufang_points_total;
	private Integer guangqian_points_total;
	
	private Integer line_total;
	private Integer line35_total;
	private Integer line110_total;
	private Integer line220_total;
	
	private Integer channel_total;
	private Integer shipin_total;
	private Integer jingai_total;
	private Integer huanjing_total;
	
	public Integer getROW_ID() {
		return ROW_ID;
	}
	public void setROW_ID(Integer rOW_ID) {
		ROW_ID = rOW_ID;
	}
	public Integer getAlarm_total() {
		return alarm_total;
	}
	public void setAlarm_total(Integer alarm_total) {
		this.alarm_total = alarm_total;
	}
	public Integer getAlarm_deal() {
		return alarm_deal;
	}
	public void setAlarm_deal(Integer alarm_deal) {
		this.alarm_deal = alarm_deal;
	}
	public Integer getAlarm_not_deal() {
		return alarm_not_deal;
	}
	public void setAlarm_not_deal(Integer alarm_not_deal) {
		this.alarm_not_deal = alarm_not_deal;
	}
	public Integer getPoint_total() {
		return point_total;
	}
	public void setPoint_total(Integer point_total) {
		this.point_total = point_total;
	}
	public Integer getPoint_online() {
		return point_online;
	}
	public void setPoint_online(Integer point_online) {
		this.point_online = point_online;
	}
	public Integer getPoint_not_online() {
		return point_not_online;
	}
	public void setPoint_not_online(Integer point_not_online) {
		this.point_not_online = point_not_online;
	}
	public Integer getHuanliu_points_total() {
		return huanliu_points_total;
	}
	public void setHuanliu_points_total(Integer huanliu_points_total) {
		this.huanliu_points_total = huanliu_points_total;
	}
	public Integer getJufang_points_total() {
		return jufang_points_total;
	}
	public void setJufang_points_total(Integer jufang_points_total) {
		this.jufang_points_total = jufang_points_total;
	}
	public Integer getGuangqian_points_total() {
		return guangqian_points_total;
	}
	public void setGuangqian_points_total(Integer guangqian_points_total) {
		this.guangqian_points_total = guangqian_points_total;
	}
	public Integer getLine_total() {
		return line_total;
	}
	public void setLine_total(Integer line_total) {
		this.line_total = line_total;
	}
	public Integer getLine35_total() {
		return line35_total;
	}
	public void setLine35_total(Integer line35_total) {
		this.line35_total = line35_total;
	}
	public Integer getLine110_total() {
		return line110_total;
	}
	public void setLine110_total(Integer line110_total) {
		this.line110_total = line110_total;
	}
	public Integer getLine220_total() {
		return line220_total;
	}
	public void setLine220_total(Integer line220_total) {
		this.line220_total = line220_total;
	}
	public Integer getChannel_total() {
		return channel_total;
	}
	public void setChannel_total(Integer channel_total) {
		this.channel_total = channel_total;
	}
	public Integer getShipin_total() {
		return shipin_total;
	}
	public void setShipin_total(Integer shipin_total) {
		this.shipin_total = shipin_total;
	}
	public Integer getJingai_total() {
		return jingai_total;
	}
	public void setJingai_total(Integer jingai_total) {
		this.jingai_total = jingai_total;
	}
	public Integer getHuanjing_total() {
		return huanjing_total;
	}
	public void setHuanjing_total(Integer huanjing_total) {
		this.huanjing_total = huanjing_total;
	}
	
	
	

//	public Integer getROW_ID() {
//		return ROW_ID;
//	}

	





	
	
	
}
