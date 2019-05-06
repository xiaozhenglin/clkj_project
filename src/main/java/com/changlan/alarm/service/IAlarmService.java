package com.changlan.alarm.service;

import java.util.List;

import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;

public interface IAlarmService {
	
	//解析指标值信息	
	Boolean anylysisPointData(List<TblPoinDataEntity> pointData); 
	
	void anylysisTemperatureData(List<TblTemperatureDataEntity> temperature); 

	void sendSMSMessage(Integer pointId, Integer indicatorId,int value);

	
}
