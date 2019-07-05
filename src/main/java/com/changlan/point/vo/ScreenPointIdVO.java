package com.changlan.point.vo;

public class ScreenPointIdVO {
	private String alarm_total;
	private String alarm_deal;
	private String alarm_not_deal;
	
	private String point_id;
	private String point_name;
	private String point_address;
	
	
	public ScreenPointIdVO() {
		super();
	}
	
	
	public ScreenPointIdVO(String alarm_total, String alarm_deal, String alarm_not_deal, String point_id,
			String point_name, String point_address) {
		super();
		this.alarm_total = alarm_total;
		this.alarm_deal = alarm_deal;
		this.alarm_not_deal = alarm_not_deal;
		this.point_id = point_id;
		this.point_name = point_name;
		this.point_address = point_address;
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
	
	
	

}
