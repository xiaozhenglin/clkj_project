package com.changlan.point.vo;

public class ScreenPointsVO {
	private String point_id;
	private String point_name;
	private String point_status;
	private String long_lati; //经纬度
	private String line_id;  
	private String line_name;
	private String line_order;
			
	

	public ScreenPointsVO(String point_id, String point_name, String point_status, String long_lati, String line_id,
			String line_name, String line_order) {
		super();
		this.point_id = point_id;
		this.point_name = point_name;
		this.point_status = point_status;
		this.long_lati = long_lati;
		this.line_id = line_id;
		this.line_name = line_name;
		this.line_order = line_order;
	}


	public ScreenPointsVO() {
		super();
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
