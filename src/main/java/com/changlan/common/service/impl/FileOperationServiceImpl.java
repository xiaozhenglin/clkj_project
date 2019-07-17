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
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.pojo.TblDvdQuery;
import com.changlan.common.service.FileOperationService;
import com.changlan.common.service.ICrudService;

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
	public List<TblDvdEntity> getAll(Integer id, String name, Integer pointId) {
		Map map = new HashMap();
		if(id != null) {
			map.put("id", new ParamMatcher(id));
		}
		if(name!=null) {
			map.put("name", new ParamMatcher(name));
		}
		if(pointId!=null) {
			map.put("pointId", new ParamMatcher(pointId));
		}
		List<TblDvdEntity> all = crudService.findByMoreFiled(TblDvdEntity.class, map, true);
		return all;
	}

}
