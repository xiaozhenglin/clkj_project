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
import com.changlan.point.dao.IPointDataDao;
import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.IPointDataService;

@Service
public class PointDataServiceImpl implements IPointDataService{
	@Autowired
	private ICrudService crudService;
	
	@Autowired
	private IPointDataDao pointDataDao;

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

	@Override
	public Page<PointDataDetail> getAll(TblPoinDataEntity data, Pageable page) {
		Map map = new HashMap();
		if(data.getPointId() != null) {
			map.put("pointId", new ParamMatcher(data.getPointId()));
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
		//按条件筛选后的结果， 再选其中分页条数得到的结果
		Page datas = crudService.findByMoreFiledAndPage(TblPoinDataEntity.class, map, true, page);
		
		List<PointDataDetail> list = new ArrayList<PointDataDetail>();
		//封装信息 0-9
		for(Object o : datas) {
			TblPoinDataEntity entity = (TblPoinDataEntity)o;
			TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
			TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
			PointDataDetail detail = new PointDataDetail(entity, point, line);
			list.add(detail);
		}
		
		return new PageImpl<PointDataDetail>(list, page, datas.getTotalElements());
	}

	@Override
	public List<PointDataDetail> getTable(Date begin, Date end, Integer indicator,Integer pointId) {
		List<PointDataDetail> result = new ArrayList<PointDataDetail>();
		List<TblPoinDataEntity> list = pointDataDao.getTableData(begin,end,indicator,pointId); 
		
		//封装信息
		if(!ListUtil.isEmpty(list)) {
			for(TblPoinDataEntity entity : list) {
//				TblPointsEntity point  = (TblPointsEntity)crudService.get(entity.getPointId(), TblPointsEntity.class, true);
//				TblLinesEntity line = (TblLinesEntity)crudService.get(point.getLineId(), TblLinesEntity.class, true);
				PointDataDetail detail = new PointDataDetail(entity, null, null);
				result.add(detail);
			}
		}
		return result;
	}

}
