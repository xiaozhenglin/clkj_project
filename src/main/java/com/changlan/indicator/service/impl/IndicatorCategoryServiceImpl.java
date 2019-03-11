package com.changlan.indicator.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.indicator.service.IIndicatorCategoryService;

@Service
public class IndicatorCategoryServiceImpl implements IIndicatorCategoryService{
	
	@Autowired
	ICrudService crudService;

	@Override
	public Boolean existCatergoryName(TblIndicatorCategoriesEntity entity) {
		Map map = new HashMap();
		map.put("name", new ParamMatcher(entity.getName()));
		List<TblIndicatorCategoriesEntity> list = crudService.findByMoreFiled(TblIndicatorCategoriesEntity.class, map, true); 
		
		Integer categoryId = entity.getCategoryId(); 
		if(categoryId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblIndicatorCategoriesEntity category : list) {
				if(category != null &&  category.getCategoryId() != categoryId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<Object> getAll(Integer id) {
		Map map = new HashMap();
		if(id!=null) {
			map.put("categoryId", new ParamMatcher(id));
		}
		List<Object> all  = crudService.findByMoreFiled(TblIndicatorCategoriesEntity.class, map, true);
		return all;
	}

}
