package com.changlan.point.vo;

import java.util.Date;

public class IndicatorValueVO {
	private String value; //指标值
	private Date recordTime; //记录时间
	
	
	public IndicatorValueVO(String value, Date recordTime) {
		super();
		this.value = value;
		this.recordTime = recordTime;
//		this.indicatorCode = indicatorCode;
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

//	public String getIndicatorCode() {
//		return indicatorCode;
//	}
//
//	public void setIndicatorCode(String indicatorCode) {
//		this.indicatorCode = indicatorCode;
//	}
	
}
