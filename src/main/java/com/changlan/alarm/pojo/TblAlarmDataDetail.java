package com.changlan.alarm.pojo;

import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointsEntity;

public class TblAlarmDataDetail extends TblAlarmRuleDetail{
	
	private TblPointAlamDataEntity  alarmData;

	public TblAlarmDataDetail(TblPointAlamDataEntity alarmData,TblAlarmRuleEntity alarmRule) { 
		super(alarmRule);
		this.alarmData = alarmData;
	}

	public TblPointAlamDataEntity getAlarmData() {
		return alarmData;
	}

	public void setAlarmData(TblPointAlamDataEntity alarmData) {
		this.alarmData = alarmData;
	}


	
	
}
