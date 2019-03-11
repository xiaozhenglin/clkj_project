package com.changlan.point.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.IPointDataService;

@Service
public class PointDataServiceImpl implements IPointDataService{
	@Autowired
	private ICrudService crudService;

	@Override
	public List<PointDataDetail> getAll(TblPoinDataEntity data) {
		List<PointDataDetail> list = new ArrayList<PointDataDetail>();
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
		List<Object> all  = crudService.findByMoreFiled(TblPoinDataEntity.class, map, true);
		//封装信息
		for(Object o : all) {
			TblPoinDataEntity entity = (TblPoinDataEntity)o;
			TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
			PointDataDetail detail = new PointDataDetail(entity, point, line);
			list.add(detail);
		}
		return list;
	}

	@Override
	@Transactional
	public TblPoinDataEntity update(TblPoinDataEntity data) {
		TblPoinDataEntity result = (TblPoinDataEntity) crudService.update(data, true);
		return result;
	}

}
