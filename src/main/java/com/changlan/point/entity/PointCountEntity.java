package com.changlan.point.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class PointCountEntity {
	@Id
	private Integer ROW_ID;
	
	private Integer point_total = 0;
	private Integer point_online = 0;
	private Integer point_not_online = 0;
	
	@Transient
	private float point_online_ratio = 0;
	
	
	private Integer alarm_total = 0;
	
	@Column(name = "alarm_deal")
	private Integer alarm_deal = 0 ;
	private Integer alarm_not_deal= 0 ;
	
	@Transient
	private float alarm_deal_ratio = 0;
	
	public void setCaculate() {
		if(alarm_total!=0) {
			BigDecimal alarm_sum = new BigDecimal(alarm_total); 
			BigDecimal alarm = new BigDecimal(alarm_deal); 
			BigDecimal alarm_per = alarm.divide(alarm_sum,4, RoundingMode.HALF_UP);
			System.out.println("报警处理率"+alarm_per);
			this.alarm_deal_ratio = alarm_per.floatValue();
		}
		
		if(point_total!=0) {
			BigDecimal point_sum = new BigDecimal(point_total); 
			BigDecimal point = new BigDecimal(point_online); 
			BigDecimal point_per = point.divide(point_sum,4, RoundingMode.HALF_UP);
			System.out.println("设备在线率"+point_per);
			this.point_online_ratio = point_per.floatValue();
		}
		
	}

//	public Integer getROW_ID() {
//		return ROW_ID;
//	}

	public void setROW_ID(Integer rOW_ID) {
		ROW_ID = rOW_ID;
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

	public float getPoint_online_ratio() {
		return point_online_ratio;
	}

	public void setPoint_online_ratio(float point_online_ratio) {
		this.point_online_ratio = point_online_ratio;
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

	public float getAlarm_deal_ratio() {
		return alarm_deal_ratio;
	}

	public void setAlarm_deal_ratio(float alarm_deal_ratio) {
		this.alarm_deal_ratio = alarm_deal_ratio;
	}
	
	
	
}
