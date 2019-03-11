package com.changlan.point.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.point.service.IPointCategoryService;


@Service
public class PointCategoryServiceImpl implements IPointCategoryService{
	@Autowired
	private ICrudService crudService;
	
	@Override
	public Boolean existName(TblPointCategoryEntity entity) {
		Map map = new HashMap();
		map.put("pontCatagoryName", new ParamMatcher(entity.getPontCatagoryName()));
		List<TblPointCategoryEntity> list = crudService.findByMoreFiled(TblPointCategoryEntity.class, map, true); 
		
		Integer categoryId = entity.getPointCatgoryId(); 
		if(categoryId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblPointCategoryEntity category : list) {
				if(category != null &&  category.getPointCatgoryId() != categoryId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<TblPointCategoryEntity> getAll(TblPointCategoryEntity entity) {
		Map map = new HashMap();
		if(entity.getPointCatgoryId()!=null) {
			map.put("pointCatgoryId", new ParamMatcher(entity.getPointCatgoryId()));
		}
		List findByMoreFiled = crudService.findByMoreFiled(TblPointCategoryEntity.class, map, true); 
		return findByMoreFiled;
	}

}
