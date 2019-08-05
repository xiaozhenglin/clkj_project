package com.changlan.point.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ScreenPointEntity {
	
	@Id
	private Integer ROW_ID;
	
	private Integer point_id;
	private String point_name;
	private String point_address;
	private String long_lati; //经纬度
	
	private Integer line_id;  
	private String line_name;
	
	private String principal;
	private String company;
	private String phones;
	
	private String video_url;
	private String picture_url;
	
	private String indicators;
	
	private String status;
	
	@Transient
	private Integer alarm_deal;
	@Transient
	private Integer alarm_not_deal;
	@Transient
	private Integer alarm_total;
	@Transient
	private float alarm_deal_ratio ;
	@Transient
	private Integer early_alarm ;
	@Transient
	private Integer alarm ;
	
	public void setROW_ID(Integer rOW_ID) {
		ROW_ID = rOW_ID;
	}
	public Integer getPoint_id() {
		return point_id;
	}
	public void setPoint_id(Integer point_id) {
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
	public String getLong_lati() {
		return long_lati;
	}
	public void setLong_lati(String long_lati) {
		this.long_lati = long_lati;
	}
	public Integer getLine_id() {
		return line_id;
	}
	public void setLine_id(Integer line_id) {
		this.line_id = line_id;
	}
	public String getLine_name() {
		return line_name;
	}
	public void setLine_name(String line_name) {
		this.line_name = line_name;
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
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public String getIndicators() {
		return indicators;
	}
	public void setIndicators(String indicators) {
		this.indicators = indicators;
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
	public Integer getAlarm_total() {
		return alarm_total;
	}
	public void setAlarm_total(Integer alarm_total) {
		this.alarm_total = alarm_total;
	}
	public float getAlarm_deal_ratio() {
		return alarm_deal_ratio;
	}
	public void setAlarm_deal_ratio(float alarm_deal_ratio) {
		this.alarm_deal_ratio = alarm_deal_ratio;
	}
	public Integer getEarly_alarm() {
		return early_alarm;
	}
	public void setEarly_alarm(Integer early_alarm) {
		this.early_alarm = early_alarm;
	}
	public Integer getAlarm() {
		return alarm;
	}
	public void setAlarm(Integer alarm) {
		this.alarm = alarm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

		
}
