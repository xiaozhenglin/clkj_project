package com.changlan.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblAlarmDownRecordEntity;
import com.changlan.common.entity.TblDvdEntity;
import com.changlan.common.pojo.MatcheType;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.pojo.TblDvdQuery;
import com.changlan.common.service.FileOperationService;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;

@Service
public class FileOperationServiceImpl implements  FileOperationService{

	@Autowired
	ICrudService crudService;
	
	@Override
	public Page<TblDvdEntity> getPage(TblDvdQuery entity, Pageable page) {
		List<TblDvdEntity> tblDvd = new ArrayList<TblDvdEntity>();
		Map<String,ParamMatcher> map = new HashMap();
		if(entity.getDvd_id() != null) {
			map.put("id", new ParamMatcher(entity.getDvd_id()));
		}
		
		if(entity.getPointId()!=null) {
			map.put("pointId", new ParamMatcher(entity.getPointId()));
		}
		if(entity.getName()!=null) {
			map.put("name", new ParamMatcher(entity.getName()));
		}
		
		if(entity.getPicture_url()!=null) {
			map.put("picture_url", new ParamMatcher(entity.getPicture_url()));
		}
						
		Page result = crudService.findByMoreFiledAndPage(TblDvdEntity.class, map, true, page);
		return result;
	}

	@Override
	public List<TblDvdEntity> getAll(TblDvdQuery query) {
		Map map = new HashMap();
		if(query.getDvd_id() != null) {
			map.put("dvd_id", new ParamMatcher(query.getDvd_id()));
		}
		if(query.getBegin()!=null&& query.getEnd()!=null) {
			map.put("modifytime", new ParamMatcher(query.getBegin(),query.getEnd()));
		}
		if(StringUtil.isNotEmpty(query.getName())) {
			map.put("name", new ParamMatcher(MatcheType.LIKE, query.getName()));
		}
		if(query.getPointId()!=null) {
			map.put("pointId", new ParamMatcher(query.getPointId()));
		}
		List<TblDvdEntity> all = crudService.findByMoreFiled(TblDvdEntity.class, map, true);
		return all;
	}

}
