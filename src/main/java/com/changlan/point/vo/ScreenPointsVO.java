package com.changlan.point.vo;

public class ScreenPointsVO {
	private String point_id;
	private String point_name;
	private String point_status;
	private String long_lati; //经纬度
	
			
	public ScreenPointsVO(String point_id, String point_name, String point_status, String long_lati) {
		super();
		this.point_id = point_id;
		this.point_name = point_name;
		this.point_status = point_status;
		this.long_lati = long_lati;
	}

	public ScreenPointsVO() {
		super();
	}
			
	public String getLong_lati() {
		return long_lati;
	}

	public void setLong_lati(String long_lati) {
		this.long_lati = long_lati;
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
	public String getPoint_status() {
		return point_status;
	}
	public void setPoint_status(String point_status) {
		this.point_status = point_status;
	}
	
	
}
