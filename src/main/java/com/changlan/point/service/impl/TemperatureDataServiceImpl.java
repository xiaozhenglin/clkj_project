package com.changlan.point.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.entity.TblTemperatureDTSDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.dao.ITemperatureDataDao;
import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.pojo.TemperatureDtsDataDetail;
import com.changlan.point.pojo.TemperatureQuery;
import com.changlan.point.service.ITemperatureDataService;

@Service
public class TemperatureDataServiceImpl implements ITemperatureDataService {
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private ITemperatureDataDao temperatureDataDao;
	
	@Override
	public  List<TemperatureDataDetail> getAll(TblTemperatureDataEntity data){
		List<TemperatureDataDetail> list = new ArrayList<TemperatureDataDetail>();
		Map map = new HashMap();
		if(data.getPointId() != null) {
			map.put("pointId", new ParamMatcher(data.getPointId()));
		}
		if(data.getIndicatorId()!=null) {
			map.put("indicatorId", new ParamMatcher(data.getIndicatorId()));
		}
		if(data.getPointName() != null) {
			map.put("pointName", new ParamMatcher(data.getPointName()));
		}
		if(data.getPointDataId()!=null) {
			map.put("pointDataId", new ParamMatcher(data.getPointDataId()));
		}
		List<Object> all  = crudService.findByMoreFiled(TblTemperatureDataEntity.class, map, true);
		//封装信息
		for(Object o : all) {
			TblTemperatureDataEntity entity = (TblTemperatureDataEntity)o;
			TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
			TemperatureDataDetail detail = new TemperatureDataDetail(entity, point, line);
			list.add(detail);
		}
		return list;
	}

	@Override
	@Transactional
	public TblTemperatureDataEntity update(TblTemperatureDataEntity data) {
		TblTemperatureDataEntity result = (TblTemperatureDataEntity) crudService.update(data, true);
		return result;
	}
	
	@Override
	public Page<TemperatureDataDetail> getAll(TemperatureQuery data, Pageable page){
		Map map = new HashMap();
		if(data.getPointId() != null) {
			map.put("pointId", new ParamMatcher(data.getPointId()));
		}
		if(data.getLineId() != null) {
			map.put("lineId", new ParamMatcher(data.getLineId()));
		}
		if(data.getIndicatorId()!=null) {
			map.put("indicatorId", new ParamMatcher(data.getIndicatorId()));
		}
		if(StringUtil.isNotEmpty(data.getPointName())) {
			map.put("pointName", new ParamMatcher(MatcheType.LIKE,data.getPointName()));
		}
		if(data.getPointDataId()!=null) {
			map.put("pointDataId", new ParamMatcher(data.getPointDataId()));
		}
		//指标类别
		if(data.getCategroryId()!=null) {
			map.put("categroryId", new ParamMatcher(data.getCategroryId()));
		}
		//监控系统类别
		if(data.getPointCatagoryId()!=null) {
			map.put("pointCatagoryId", new ParamMatcher(data.getPointCatagoryId()));
		}
		if(data.getBegin()!=null&& data.getEnd()!=null) {
			map.put("recordTime", new ParamMatcher(data.getBegin(),data.getEnd()));
		}
		//按条件筛选后的结果， 再选其中分页条数得到的结果
		Page datas = crudService.findByMoreFiledAndPage(TblTemperatureDataEntity.class, map, true, page);
		
		List<TemperatureDataDetail> list = new ArrayList<TemperatureDataDetail>();
		//封装信息 0-9
		for(Object o : datas) {
			TblTemperatureDataEntity entity = (TblTemperatureDataEntity)o;
			TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
			TemperatureDataDetail detail = new TemperatureDataDetail(entity, point, line);
			list.add(detail);
		}
		
		return new PageImpl<TemperatureDataDetail>(list, page, datas.getTotalElements());
	}
	
	@Override
	public List<TemperatureDataDetail> getTable(Date begin, Date end, Integer indicator, Integer pointId){
		List<TemperatureDataDetail> result = new ArrayList<TemperatureDataDetail>();
		List<TblTemperatureDataEntity> list = temperatureDataDao.getTableData(begin,end,indicator,pointId); 
		
		//封装信息
		if(!ListUtil.isEmpty(list)) {
			for(TblTemperatureDataEntity entity : list) {
				TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
//				TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
				TemperatureDataDetail detail = new TemperatureDataDetail(entity, point, null);
				result.add(detail);
			}
		}
		return result;
	}
	
	@Override
	public List<TemperatureDtsDataDetail> getDtsTable(Date begin, Date end, Integer indicator, Integer pointId){
		List<TemperatureDtsDataDetail> result = new ArrayList<TemperatureDtsDataDetail>();
		List<TblTemperatureDTSDataEntity> list = temperatureDataDao.getDtsTableData(begin,end,indicator,pointId); 
		
		//封装信息
		if(!ListUtil.isEmpty(list)) {
			for(TblTemperatureDTSDataEntity entity : list) {
				TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
//				TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
				TemperatureDtsDataDetail detail = new TemperatureDtsDataDetail(entity, point, null);
				result.add(detail);
			}
		}
		return result;
	}
	

}
