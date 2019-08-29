package com.changlan.point.vo;

import java.util.Date;

public class IndicatorValueVO {
	private String value; //指标值
	private Date recordTime; //记录时间
	private Integer id;
				
	public IndicatorValueVO(String value, Date recordTime, Integer id) {
		super();
		this.value = value;
		this.recordTime = recordTime;
		if(id!=null) {
			this.id = id;
		}
	}

	public IndicatorValueVO() {
		super();
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
//	public String getIndicatorCode() {
//		return indicatorCode;
//	}
//
//	public void setIndicatorCode(String indicatorCode) {
//		this.indicatorCode = indicatorCode;
//	}
	
}
