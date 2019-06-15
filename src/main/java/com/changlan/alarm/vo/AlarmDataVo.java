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
	private Integer pointDataId;
	private String  indicatorValue;
	
	public AlarmDataVo(TblAlarmDataDetail detail) {
		TblPointsEntity point = detail.getPoint(); 
		TblPointAlamDataEntity alarmData = detail.getAlarmData(); 
		TblAlarmRuleEntity alarmRule = detail.getAlarmRule(); 
		TblIndicatorValueEntity indicator = detail.getIndicator(); 
		this.pointId = pointId;
		this.pointName = pointName;
		this.indicatorId = indicatorId;
		this.indicatorName = indicatorName;
		this.pointDataId = pointDataId;
		this.indicatorValue = indicatorValue;
		
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
	public Integer getPointDataId() {
		return pointDataId;
	}
	public void setPointDataId(Integer pointDataId) {
		this.pointDataId = pointDataId;
	}
	public String getIndicatorValue() {
		return indicatorValue;
	}
	public void setIndicatorValue(String indicatorValue) {
		this.indicatorValue = indicatorValue;
	}
	
}
