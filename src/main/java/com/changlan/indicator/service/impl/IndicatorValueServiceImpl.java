package com.changlan.indicator.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.alarm.service.IAlarmService;
import com.changlan.command.service.impl.CommandRecordServiceImpl;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointAlamDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.pojo.IndiCatorValueDetail;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.IPointDefineService;

@Service
public class IndicatorValueServiceImpl implements IIndicatoryValueService{
	
	private static Logger log = LoggerFactory.getLogger(CommandRecordServiceImpl.class);
	
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IAlarmService alarmService;
	
	@Autowired
	private IPointDefineService pointDefineService;


	@Override
	public Boolean existName(TblIndicatorValueEntity entity) {
		Map map = new HashMap();
		map.put("indicatorCode", new ParamMatcher(entity.getIndicatorCode()));
		map.put("name", new ParamMatcher(entity.getName()));
		List<TblIndicatorValueEntity> list = crudService.findByMoreFiled(TblIndicatorValueEntity.class, map, true); 
		
		Integer valueId = entity.getIndicatorId(); 
		if(valueId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblIndicatorValueEntity value : list) {
				if(value != null &&  value.getIndicatorId() != valueId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<IndiCatorValueDetail> getAll(Integer id,Integer categoryId) {
		List<IndiCatorValueDetail> list = new ArrayList<IndiCatorValueDetail>();
		Map map = new HashMap();
		if(id != null) {
			map.put("indicatorId", new ParamMatcher(id));
		}
		if(categoryId!=null) {
			map.put("categoryId", new ParamMatcher(categoryId));
		}
		List<Object> all = crudService.findByMoreFiled(TblIndicatorValueEntity.class, map, true);
		//封装公司信息和公司组信息
		for(Object o : all) {
			TblIndicatorValueEntity entity = (TblIndicatorValueEntity)o;
			IndiCatorValueDetail detail = new IndiCatorValueDetail(entity);
			list.add(detail);
		}
		return list;
	}
	
	
}
