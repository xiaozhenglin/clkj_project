package com.changlan.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserGroupEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.service.IUserGoupService;

@Service
public class UserGoupServiceImpl implements IUserGoupService{
	
	@Autowired
	ICrudService crudService;

	@Override
	public Boolean existName(TblUserGroupEntity userGroup) {
		Map map = new HashMap();
		map.put("name", new ParamMatcher(userGroup.getName()));
		List<TblUserGroupEntity> list = crudService.findByMoreFiled(TblUserGroupEntity.class, map, true); 
		Integer userGoupId = userGroup.getUserGroupId(); 
		if(userGoupId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblUserGroupEntity userGoup : list) {
				if(userGoup != null &&  userGoupId!=userGoup.getUserGroupId() ) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<TblUserGroupEntity> getAll(TblUserGroupEntity userGroup) {
		Map map = new HashMap();
		if(StringUtil.isNotEmpty(userGroup.getName())) {
			map.put("name", new ParamMatcher(userGroup.getName()));
		}
		if(userGroup.getUserGroupId()!=null) {
			map.put("userGroupId", new ParamMatcher(userGroup.getUserGroupId()));
		}
		List<TblUserGroupEntity> list = crudService.findByMoreFiled(TblUserGroupEntity.class, map, true); 
		return list;
	}

}
