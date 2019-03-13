package com.changlan.alarm.service;

import java.util.List;

import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;

public interface IAlarmService {
	
	//解析指标值信息	
	Boolean anylysisPointData(List<TblPoinDataEntity> pointData); 


	void sendSMSMessage(Integer pointId, Integer indicatorId);

}
