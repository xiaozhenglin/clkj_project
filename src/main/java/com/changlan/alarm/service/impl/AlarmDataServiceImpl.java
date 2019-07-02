package com.changlan.alarm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.alarm.pojo.AlarmDownRecordQuery;
import com.changlan.alarm.pojo.TblAlarmDataDetail;
import com.changlan.alarm.service.IAlarmDataService;
import com.changlan.common.entity.TBLAlarmCategoryEntity;
import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;

@Service
public class AlarmDataServiceImpl implements IAlarmDataService {
	
	@Autowired
	ICrudService crudService;

	@Override
	public List<TblPointAlamDataEntity> getList(TblPointAlamDataEntity entity) {
		Map map = new HashMap();
		if(entity.getAlarmId() != null) {
			map.put("alarmId", new ParamMatcher(entity.getAlarmId()));
		}
		if(entity.getIndicatorId()!=null) {
			map.put("indicatorValueId", new ParamMatcher(entity.getIndicatorId()));
		}
		if(entity.getAlarmRuleId()!=null) {
			map.put("alarmRuleId", new ParamMatcher(entity.getAlarmRuleId()));
		}
		List<TblPointAlamDataEntity> all = crudService.findByMoreFiled(TblPointAlamDataEntity.class, map, true);
		return all;
	}

	@Override
	public TblAlarmDataDetail getDetail(Integer id) {
		TblPointAlamDataEntity alarmData = (TblPointAlamDataEntity)crudService.get(id, TblPointAlamDataEntity.class, true);
		TblAlarmRuleEntity alarmRule = (TblAlarmRuleEntity)crudService.get(alarmData.getAlarmRuleId(), TblAlarmRuleEntity.class, true);
		TblAlarmDataDetail detail = new TblAlarmDataDetail(alarmData,alarmRule);
		return detail;
	}

	@Override
	public Page<TblAlarmDataDetail> getPage(TblPointAlamDataEntity entity, Pageable pageable) {
		Page<TblAlarmDataDetail> result ;
		Map map = new HashMap();
		if(entity.getAlarmId() != null) {
			map.put("alarmId", new ParamMatcher(entity.getAlarmId()));
		}
		if(entity.getIndicatorId()!=null) {
			map.put("indicatorId", new ParamMatcher(entity.getIndicatorId()));
		}
		if(entity.getAlarmRuleId()!=null) {
			map.put("alarmRuleId", new ParamMatcher(entity.getAlarmRuleId()));
		}
		if(entity.getPointId() !=null) {
			map.put("pointId", new ParamMatcher(entity.getPointId()));
		}
		Page datas = crudService.findByMoreFiledAndPage(TblPointAlamDataEntity.class, map, true, pageable);
		
		List<TblAlarmDataDetail> list = new ArrayList<TblAlarmDataDetail>();
		for(Object o : datas) {
			TblPointAlamDataEntity alarmData =(TblPointAlamDataEntity)o;
			TblAlarmRuleEntity alarmRule = (TblAlarmRuleEntity)crudService.get(alarmData.getAlarmRuleId(), TblAlarmRuleEntity.class, true);
			TblAlarmDataDetail detail = new TblAlarmDataDetail(alarmData,alarmRule);
			list.add(detail);
		}
		result = new PageImpl<TblAlarmDataDetail>(list, pageable, datas.getTotalElements());
		return result;
	}

	@Override
	public Page getPage(AlarmDownRecordQuery query, Pageable page) {
		Map map = new HashMap();
		if(query.getAlamDownRecordId() != null) {
			map.put("alamDownRecordId", new ParamMatcher(query.getAlamDownRecordId()));
		}
		if(query.getAlarmDataId()!=null) {
			map.put("alarmDataId", new ParamMatcher(query.getAlarmDataId()));
		}
		if(query.getBegin()!=null && query.getEnd()!=null) {
			map.put("recordTime", new ParamMatcher(query.getBegin(),query.getEnd()));
		}
		Page result = crudService.findByMoreFiledAndPage(TblAlarmDownRecordEntity.class, map, true, page);
		return result;
	}
	

}
