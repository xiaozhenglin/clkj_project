package com.changlan.command.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.command.pojo.CommandCategoryDetail;
import com.changlan.command.service.ICommandCategoryService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.point.pojo.CompanyDetail;

@Service
public class CommandCategoryServiceImpl implements ICommandCategoryService {

	@Autowired
	ICrudService crudService;

	@Override
	public Boolean existName(TblCommandCategoryEntity entity) {
		Map map = new HashMap();
		map.put("categoryNmae", new ParamMatcher(entity.getCategoryNmae()));
		List<TblCommandCategoryEntity> list = crudService.findByMoreFiled(TblCommandCategoryEntity.class, map, true); 
		Integer commandCatagoryId = entity.getCommandCatagoryId(); 
		if(commandCatagoryId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblCommandCategoryEntity category : list) {
				if(category != null &&  category.getCommandCatagoryId() != commandCatagoryId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public TblCommandCategoryEntity save(TblCommandCategoryEntity category) {
		TblCommandCategoryEntity update = (TblCommandCategoryEntity)crudService.update(category, true);
		return update;
	}

	@Override
	public List<TblCommandCategoryEntity> categoryList(Integer id) {
		List<TblCommandCategoryEntity> result = new ArrayList();
		List<Object> all = new ArrayList<Object>();
		Map map = new HashMap();
		if(id != null) {
			map.put("commandCatagoryId", new ParamMatcher(id));
		}
		all = crudService.findByMoreFiled(TblCommandCategoryEntity.class, map, true);
		for(Object o : all) {
			TblCommandCategoryEntity entity = (TblCommandCategoryEntity)o;
//			CommandCategoryDetail detail = new CommandCategoryDetail(entity);
			result.add(entity);
		}
		return result;
	}

}
