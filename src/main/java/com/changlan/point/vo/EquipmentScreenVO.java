package com.changlan.point.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import com.changlan.common.entity.TblAlarmDownRecordEntity;

public class EquipmentScreenVO {
	
	private String alarm_deal;
	private String alarm_not_deal;

	
	private String point_id;
	private String point_name;
	private String point_address;
	private String phones;
	
	private String principal;
	
	private String company;
	private String point_catagory_name;
	private String indicators;
	
    		


	public EquipmentScreenVO(String alarm_deal, String alarm_not_deal, String point_id, String point_name,
			String point_address, String phones, String principal, String company, String point_catagory_name,
			String indicators) {
		super();
		this.alarm_deal = alarm_deal;
		this.alarm_not_deal = alarm_not_deal;
		this.point_id = point_id;
		this.point_name = point_name;
		this.point_address = point_address;
		this.phones = phones;
		this.principal = principal;
		this.company = company;
		this.point_catagory_name = point_catagory_name;
		this.indicators = indicators;
	}


	public EquipmentScreenVO() {
		super();
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
	
	public String getPoint_id() {
		return point_id;
	}


	public void setPoint_id(String point_id) {
		this.point_id = point_id;
	}


	public String getPoint_name() {
		return point_name;
	}
	public void setPoint_name(String point_name) {
		this.point_name = point_name;
	}
	public String getPoint_address() {
		return point_address;
	}
	public void setPoint_address(String point_address) {
		this.point_address = point_address;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPoint_catagory_name() {
		return point_catagory_name;
	}
	public void setPoint_catagory_name(String point_catagory_name) {
		this.point_catagory_name = point_catagory_name;
	}
	public String getIndicators() {
		return indicators;
	}
	public void setIndicators(String indicators) {
		this.indicators = indicators;
	}
	
		
	
}
