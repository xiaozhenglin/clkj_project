package com.changlan.alarm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.alarm.pojo.TblAlarmRuleDetail;
import com.changlan.alarm.service.IAlarmRuleService;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;

@Service
public class AlarmRuleServiceImpl implements IAlarmRuleService{
	@Autowired
	ICrudService crudService;

	@Override
	public List<TblAlarmRuleEntity> getAll(Integer ruleId, Integer indicatorValueId, Integer pointId) {
		Map map = new HashMap();
		if(ruleId != null) {
			map.put("alarmRuleId", new ParamMatcher(ruleId));
		}
		if(indicatorValueId!=null) {
			map.put("indicatorValueId", new ParamMatcher(indicatorValueId));
		}
		if(pointId!=null) {
			map.put("pointId", new ParamMatcher(pointId));
		}
		List<TblAlarmRuleEntity> all = crudService.findByMoreFiled(TblAlarmRuleEntity.class, map, true);
		return all;
	}

	@Override
	public Boolean existName(TblAlarmRuleEntity entity) {
		Map map = new HashMap();
		map.put("ruleName", new ParamMatcher(entity.getRuleName()));
		List<TblAlarmRuleEntity> list = crudService.findByMoreFiled(TblAlarmRuleEntity.class, map, true); 
		
		Integer valueId = entity.getAlarmRuleId(); 
		if(valueId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblAlarmRuleEntity value : list) {
				if(value != null &&  value.getAlarmRuleId() != valueId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public TblAlarmRuleDetail getDetail(Integer id) {
		TblAlarmRuleEntity alarmRule = (TblAlarmRuleEntity)crudService.get(id, TblAlarmRuleEntity.class, true);
		TblAlarmRuleDetail detail = new TblAlarmRuleDetail(alarmRule);
		return detail;
	}
	
}
