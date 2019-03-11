package com.changlan.point.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.CompanyDetail;
import com.changlan.point.service.ICompanyGropService;

@Service
public class CompanyGroupServiceImpl implements ICompanyGropService{
	
	@Autowired
	ICrudService crudService;

	@Override
	public Boolean existGroupName(TblCompanyGroupEntity entity) {
		Map map = new HashMap();
		map.put("name", new ParamMatcher(entity.getName()));
		List<TblCompanyGroupEntity> list = crudService.findByMoreFiled(TblCompanyGroupEntity.class, map, true); 
		
		Integer groupId = entity.getGroupId(); 
		if(groupId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblCompanyGroupEntity companyGroup : list) {
				if(companyGroup != null &&  companyGroup.getGroupId() != groupId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<TblCompanyGroupEntity> getAllGroup(TblCompanyGroupEntity group) {
		List<TblCompanyGroupEntity> all = null;
		Map map = new HashMap();
		if(group.getGroupId() != null) {
			map.put("groupId", new ParamMatcher(group.getGroupId()));
		}
		if(StringUtil.isNotEmpty(group.getName())) {
			map.put("name", new ParamMatcher(group.getName()));
		}
		all = crudService.findByMoreFiled(TblCompanyGroupEntity.class, map, true);
		//封装公司信息和公司组信息
		return all;
	}
}
