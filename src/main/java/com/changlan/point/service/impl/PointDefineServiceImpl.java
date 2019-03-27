package com.changlan.point.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.ICompanyGropService;
import com.changlan.point.service.IPointDefineService;

@Service
public class PointDefineServiceImpl implements IPointDefineService{
	
	@Autowired
	private ICrudService crudService;
	
	@Override
	public List<PointInfoDetail> getAll(TblPointsEntity point) {
		List<PointInfoDetail> list = new ArrayList<PointInfoDetail>();
		List<Object> all = null;
		Map map = new HashMap();
		if(point.getPointId() != null) {
			map.put("pointId", new ParamMatcher(point.getPointId()));
		}
		if(point.getLineId()!=null) {
			map.put("lineId", new ParamMatcher(point.getLineId()));
		}
		all = crudService.findByMoreFiled(TblPointsEntity.class, map, true);
		//封装公司信息和公司组信息
		for(Object o : all) {
			TblPointsEntity entity = (TblPointsEntity)o;
			TblLinesEntity line = (TblLinesEntity)crudService.get(entity.getLineId(), TblLinesEntity.class, true);
			PointInfoDetail detail = new PointInfoDetail(entity,line);
			list.add(detail);
		}
		return list;
	}

	@Override
	public Boolean existPointpName(TblPointsEntity entity) {
		Map map = new HashMap();
		map.put("pointName", new ParamMatcher(entity.getPointName()));
		List<TblPointsEntity> list = crudService.findByMoreFiled(TblPointsEntity.class, map, true); 
		Integer pointId = entity.getPointId(); 
		if(pointId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblPointsEntity point : list) {
				if(point != null &&  point.getPointId() != pointId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Page<PointInfoDetail> getPage(TblPointsEntity point, Pageable pageable) {
		Map map = new HashMap();
		if(point.getPointId() != null) {
			map.put("pointId", new ParamMatcher(point.getPointId()));
		}
		if(point.getLineId()!=null) {
			map.put("lineId", new ParamMatcher(point.getLineId()));
		}
		if(point.getPointCatagoryId()!=null) {
			map.put("pointCatagoryId", new ParamMatcher(point.getPointCatagoryId()));
		}
		Page<TblPointsEntity> all = crudService.findByMoreFiledAndPage(TblPointsEntity.class, map, true,pageable);
		
		List<PointInfoDetail> list = new ArrayList<PointInfoDetail>();
		//封装公司信息和公司组信息
		for(Object o : all) {
			TblPointsEntity entity = (TblPointsEntity)o;
			TblLinesEntity line = (TblLinesEntity)crudService.get(entity.getLineId(), TblLinesEntity.class, true);
			PointInfoDetail detail = new PointInfoDetail(entity,line);
			list.add(detail);
		}
		return new PageImpl<PointInfoDetail>(list, pageable, all.getTotalElements()); 
	}

	@Override
	public TblPointsEntity getByRegistPackageOrId(Integer pointId, String registPackage) {
		Map map = new HashMap();
		if(pointId != null) {
			map.put("pointId", new ParamMatcher(pointId));
		}
		if(StringUtil.isNotEmpty(registPackage)) { 
			map.put("pointRegistPackage", new ParamMatcher(registPackage));
		}
		List findByMoreFiled = crudService.findByMoreFiled(TblPointsEntity.class, map, true); 
		if(ListUtil.isEmpty(findByMoreFiled)) {
			return null;
		}
		return (TblPointsEntity)findByMoreFiled.get(0);  
	}

}
