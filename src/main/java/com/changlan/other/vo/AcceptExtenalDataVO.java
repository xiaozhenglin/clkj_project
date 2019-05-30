package com.changlan.other.vo;

import java.io.Serializable;
import java.util.Date;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;

public class AcceptExtenalDataVO implements Serializable{
	
	private Integer pointId;
	private Integer indicatorCategoryId;
	private Integer indicatorId;
	private Date begin;
	private Date end;
	private Double value;
	
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public Integer getIndicatorCategoryId() {
		return indicatorCategoryId;
	}
	public void setIndicatorCategoryId(Integer indicatorCategoryId) {
		this.indicatorCategoryId = indicatorCategoryId;
	}
	public Integer getIndicatorId() {
		return indicatorId;
	}
	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		value = value;
	}
	
	public TblPoinDataEntity toEntity() { 
		TblPoinDataEntity entity = new TblPoinDataEntity();
		entity.setPointId(pointId);
		entity.setCategroryId(indicatorCategoryId);
		entity.setIndicatorId(indicatorId);
		entity.setValue(value+"");
		entity.setRecordTime(begin);
		return entity;
	}
	
	public TblTemperatureDataEntity toTemeratureDataEntity() {
		TblTemperatureDataEntity entity = new TblTemperatureDataEntity();
		entity.setPointId(pointId);
		entity.setCategroryId(indicatorCategoryId);
		entity.setIndicatorId(indicatorId);
		entity.setValue(value+"");
		entity.setRecordTime(begin);
		return entity;
	}
	
	
	
}
