package com.changlan.point.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.other.entity.DeviceDataColl;
import com.changlan.point.dao.IPartDischargeDataDao;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PartDischargeQuery;
import com.changlan.point.service.IPartDischargeDataService;


@Service
public class PartDischargeDataServiceImpl implements IPartDischargeDataService{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPartDischargeDataDao partDischargeDataDao;

	@Override
	public List<PartDischargeDataDetail> getAll(DeviceDataColl data) {
		List<PartDischargeDataDetail> list = new ArrayList<PartDischargeDataDetail>();
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
		List<Object> all  = crudService.findByMoreFiled(DeviceDataColl.class, map, true);
		//封装信息
		for(Object o : all) {
			DeviceDataColl entity = (DeviceDataColl)o;
			TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
			PartDischargeDataDetail detail = new PartDischargeDataDetail(entity, point, line);
			list.add(detail);
		}
		return list;
	}

	@Override
	@Transactional
	public DeviceDataColl update(DeviceDataColl data) {
		DeviceDataColl result = (DeviceDataColl) crudService.update(data, true);
		return result;
	}

	@Override
	public Page<PartDischargeDataDetail> getAll(PartDischargeQuery data, Pageable page) {
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
		Page datas = crudService.findByMoreFiledAndPage(DeviceDataColl.class, map, true, page);
		
		List<PartDischargeDataDetail> list = new ArrayList<PartDischargeDataDetail>();
		//封装信息 0-9
		for(Object o : datas) {
			DeviceDataColl entity = (DeviceDataColl)o;
			TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
			PartDischargeDataDetail detail = new PartDischargeDataDetail(entity, point, line);
			list.add(detail);
		}
		
		return new PageImpl<PartDischargeDataDetail>(list, page, datas.getTotalElements());
	}

	@Override
	public List<PartDischargeDataDetail> getTable(Date begin, Date end, Integer indicator,Integer pointId) {
		List<PartDischargeDataDetail> result = new ArrayList<PartDischargeDataDetail>();
		List<DeviceDataColl> list = partDischargeDataDao.getTableData(begin,end,indicator,pointId); 
		
		//封装信息
		if(!ListUtil.isEmpty(list)) {
			for(DeviceDataColl entity : list) {
				TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
//				TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
				PartDischargeDataDetail detail = new PartDischargeDataDetail(entity, point, null);
				result.add(detail);
			}
		}
		return result;
	}

}
