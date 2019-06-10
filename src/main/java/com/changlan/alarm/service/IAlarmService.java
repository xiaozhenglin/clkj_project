package com.changlan.alarm.service;

import java.util.List;

import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;

public interface IAlarmService {
	
	//电流电压报警解析	
	Boolean anylysisPointData(List<TblPoinDataEntity> pointData); 
	
	//温度报警解析	
	void anylysisTemperatureData(List<TblTemperatureDataEntity> temperature); 
	
	//局放报警解析

	//发送sms消息
	void sendSMSMessage(Integer pointId, Integer indicatorId,int value);
	
	
}
