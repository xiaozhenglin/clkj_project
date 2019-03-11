package com.changlan.indicator.pojo;

public class SimpleIndicator {
	
	private Integer IndicatorId;
	private String IndicatorValue;
	private Integer pointDataId;
	
	
	
	
	public SimpleIndicator(Integer indicatorId, String indicatorValue, Integer pointDataId) {
		super();
		IndicatorId = indicatorId;
		IndicatorValue = indicatorValue;
		this.pointDataId = pointDataId;
	}

	public SimpleIndicator(Integer indicatorId, String IndicatorValue) { 
		super();
		IndicatorId = indicatorId;
		IndicatorValue = IndicatorValue;
	}
	
	public SimpleIndicator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIndicatorId() {
		return IndicatorId;
	}
	public void setIndicatorId(Integer indicatorId) {
		IndicatorId = indicatorId;
	}
	public String getIndicatorValue() {
		return IndicatorValue;
	}
	public void setIndicatorValue(String indicatorValue) {
		IndicatorValue = indicatorValue;
	}

	public Integer getPointDataId() {
		return pointDataId;
	}

	public void setPointDataId(Integer pointDataId) {
		this.pointDataId = pointDataId;
	}
	
	
	
}
