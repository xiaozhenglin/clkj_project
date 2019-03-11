package com.changlan.alarm.service;

import java.util.List;

import com.changlan.alarm.pojo.TblAlarmRuleDetail;
import com.changlan.common.entity.TblAlarmRuleEntity;

public interface IAlarmRuleService {

	List<TblAlarmRuleEntity> getAll(Integer id, Integer indicatorValueId, Integer pointId); 

	Boolean existName(TblAlarmRuleEntity entity);

	TblAlarmRuleDetail getDetail(Integer id); 

}
