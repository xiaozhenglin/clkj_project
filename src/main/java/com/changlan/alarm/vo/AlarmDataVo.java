package com.changlan.alarm.vo;

import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointsEntity;

public class AlarmDataVo {
	
	private TblPointAlamDataEntity alarmData;
	
	public AlarmDataVo(TblAlarmDataDetail detail) {
		TblPointsEntity point = detail.getPoint(); 
		TblPointAlamDataEntity alarmData = detail.getAlarmData(); 
		TblAlarmRuleEntity alarmRule = detail.getAlarmRule(); 
		TblIndicatorValueEntity indicator = detail.getIndicator(); 
		this.alarmData = alarmData;
		this.alarmData.setUnit(indicator.getUnit());
		this.alarmData.setPointName(point.getPointName());
		this.alarmData.setIndicatorName(indicator.getName());
	}
	
	public AlarmDataVo() {
		super();
	}

	public TblPointAlamDataEntity getAlarmData() {
		return alarmData;
	}

	public void setAlarmData(TblPointAlamDataEntity alarmData) {
		this.alarmData = alarmData;
	}
	
	
	
}
