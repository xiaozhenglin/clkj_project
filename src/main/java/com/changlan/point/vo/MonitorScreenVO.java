package com.changlan.point.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import com.changlan.common.entity.TblAlarmDownRecordEntity;

public class MonitorScreenVO {
	
	private String alarm_total;
	private String alarm_deal;
	private String alarm_not_deal;
	private float alarm_deal_ratio;
	
	private String point_total;
	private String point_online;
	private String point_not_online;
	private float point_online_ratio;
	
	public void setCaculate() {
		BigDecimal alarm_sum = new BigDecimal(alarm_total); 
		BigDecimal alarm = new BigDecimal(alarm_deal); 
		
		BigDecimal alarm_per = alarm.divide(alarm_sum,4, RoundingMode.HALF_UP);
				
		System.out.println("报警处理率"+alarm_per);
		this.alarm_deal_ratio = alarm_per.floatValue();
		
		
		BigDecimal point_sum = new BigDecimal(point_total); 
		BigDecimal point = new BigDecimal(point_online); 
		
		BigDecimal point_per = point.divide(point_sum,4, RoundingMode.HALF_UP);
		System.out.println("设备在线率"+point_per);
		this.point_online_ratio = point_per.floatValue();
	}
	
	
	
	public MonitorScreenVO(String alarm_total, String alarm_deal, String alarm_not_deal, float alarm_deal_ratio,
			String point_total, String point_online, String point_not_online, float point_online_ratio) {
		super();
		this.alarm_total = alarm_total;
		this.alarm_deal = alarm_deal;
		this.alarm_not_deal = alarm_not_deal;
		this.alarm_deal_ratio = alarm_deal_ratio;
		this.point_total = point_total;
		this.point_online = point_online;
		this.point_not_online = point_not_online;
		this.point_online_ratio = point_online_ratio;
	}



	public MonitorScreenVO() {
		super();
	}

	public String getAlarm_total() {
		return alarm_total;
	}

	public void setAlarm_total(String alarm_total) {
		this.alarm_total = alarm_total;
	}

	public String getAlarm_deal() {
		return alarm_deal;
	}

	public void setAlarm_deal(String alarm_deal) {
		this.alarm_deal = alarm_deal;
	}

	public String getAlarm_not_deal() {
		return alarm_not_deal;
	}

	public void setAlarm_not_deal(String alarm_not_deal) {
		this.alarm_not_deal = alarm_not_deal;
	}

	public float getAlarm_deal_ratio() {
		return alarm_deal_ratio;
	}

	public void setAlarm_deal_ratio(float alarm_deal_ratio) {
		this.alarm_deal_ratio = alarm_deal_ratio;
	}

	public String getPoint_total() {
		return point_total;
	}

	public void setPoint_total(String point_total) {
		this.point_total = point_total;
	}

	public String getPoint_online() {
		return point_online;
	}

	public void setPoint_online(String point_online) {
		this.point_online = point_online;
	}

	public String getPoint_not_online() {
		return point_not_online;
	}

	public void setPoint_not_online(String point_not_online) {
		this.point_not_online = point_not_online;
	}

	public float getPoint_online_ratio() {
		return point_online_ratio;
	}

	public void setPoint_online_ratio(float point_online_ratio) {
		this.point_online_ratio = point_online_ratio;
	}
	
	
	
}
