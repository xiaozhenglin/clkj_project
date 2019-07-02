package com.changlan.alarm.vo;

import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointsEntity;

public class AlarmDataVo {

	private Integer pointId;
	private String  pointName;
	private Integer indicatorId;
	private String  indicatorName;
	private String  unit; //指标单位
	private TblPointAlamDataEntity alarmData;
	
	public AlarmDataVo(TblAlarmDataDetail detail) {
		TblPointsEntity point = detail.getPoint(); 
		TblPointAlamDataEntity alarmData = detail.getAlarmData(); 
		TblAlarmRuleEntity alarmRule = detail.getAlarmRule(); 
		TblIndicatorValueEntity indicator = detail.getIndicator(); 
		this.pointId = point.getPointId();
		this.pointName = point.getPointName();
		this.indicatorId = indicator.getIndicatorId();
		this.indicatorName = indicator.getName();
		this.unit = indicator.getUnit();
		this.alarmData = alarmData;
	}
	
	public AlarmDataVo() {
		super();
	}
	
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public Integer getIndicatorId() {
		return indicatorId;
	}
	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}
	public String getIndicatorName() {
		return indicatorName;
	}
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public TblPointAlamDataEntity getAlarmData() {
		return alarmData;
	}

	public void setAlarmData(TblPointAlamDataEntity alarmData) {
		this.alarmData = alarmData;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
