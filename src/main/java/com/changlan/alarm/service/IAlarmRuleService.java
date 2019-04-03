package com.changlan.alarm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.alarm.pojo.TblAlarmRuleDetail;
import com.changlan.common.entity.TblAlarmRuleEntity;

public interface IAlarmRuleService {

	List<TblAlarmRuleEntity> getAll(Integer id, Integer indicatorValueId, Integer pointId); 

	Boolean existName(TblAlarmRuleEntity entity);

	TblAlarmRuleDetail getDetail(Integer id);

	Page<TblAlarmRuleDetail> getPage(TblAlarmRuleEntity entity, Pageable page);  

}
