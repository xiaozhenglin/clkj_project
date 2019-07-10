package com.changlan.point.vo;

public class ScreenPointIdVO {
	private String alarm_total;
	private String alarm_deal;
	private String alarm_not_deal;
	
	private String point_id;
	private String point_name;
	private String point_address;
	private String long_lati; //经纬度
	
	private String line_id;  
	private String line_name;
	private String line_order;
	
	public ScreenPointIdVO() {
		super();
	}
	
		

	public ScreenPointIdVO(String alarm_total, String alarm_deal, String alarm_not_deal, String point_id,
			String point_name, String point_address, String long_lati, String line_id, String line_name,
			String line_order) {
		super();
		this.alarm_total = alarm_total;
		this.alarm_deal = alarm_deal;
		this.alarm_not_deal = alarm_not_deal;
		this.point_id = point_id;
		this.point_name = point_name;
		this.point_address = point_address;
		this.long_lati = long_lati;
		this.line_id = line_id;
		this.line_name = line_name;
		this.line_order = line_order;
	}


	public String getLine_id() {
		return line_id;
	}


	public void setLine_id(String line_id) {
		this.line_id = line_id;
	}


	public String getLine_name() {
		return line_name;
	}


	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}


	public String getLine_order() {
		return line_order;
	}


	public void setLine_order(String line_order) {
		this.line_order = line_order;
	}


	public String getLong_lati() {
		return long_lati;
	}

	public void setLong_lati(String long_lati) {
		this.long_lati = long_lati;
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
