package com.changlan.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IUserRoleService;

@Service
public class UserRoleServiceImpl implements IUserRoleService{
	@Autowired
	ICrudService crudService;

	@Override
	public UserRoleDetail getOne(String adminUserId) {
		Map map = new HashMap();
		map.put("userId", new ParamMatcher(adminUserId));
		List<TBLUserRoleEntity> list = crudService.findByMoreFiled(TBLUserRoleEntity.class, map, true); 
		if(!ListUtil.isEmpty(list)) {
			TBLUserRoleEntity UserRoleEntity = list.get(0); 
			TBLRoleDefineEntity role = (TBLRoleDefineEntity)crudService.get(UserRoleEntity.getRoleID(), TBLRoleDefineEntity.class, true);
			UserRoleDetail detail = new UserRoleDetail(UserRoleEntity,role);
			return detail;
		}
		
		return null;
	}

	@Override
	public Boolean existRole(TBLUserRoleEntity role) {
		Map map = new HashMap();
		map.put("userId", new ParamMatcher(role.getUserId()));
		map.put("roleID", new ParamMatcher(role.getRoleID()));
		List<TBLUserRoleEntity> list = crudService.findByMoreFiled(TBLUserRoleEntity.class, map, true); 
		if(ListUtil.isEmpty(list)) {
			return false;
		}
		return true;
	}

	@Override
	public List<UserRoleDetail> getAll(TBLUserRoleEntity role) {
		List<UserRoleDetail> result  = new ArrayList<UserRoleDetail>();
		Map map = new HashMap();
		if(role!=null) {
			if(StringUtil.isNotEmpty(role.getUserId())) {
				map.put("userId", new ParamMatcher(role.getUserId()));
			}
			if(role.getRoleID()!=null) {
				map.put("roleID", new ParamMatcher(role.getRoleID()));
			}
			if(role.getUserRoleId()!=null) {
				map.put("userRoleId", new ParamMatcher(role.getUserRoleId()));
			}
		}
		List<TBLUserRoleEntity> list = crudService.findByMoreFiled(TBLUserRoleEntity.class, map, true); 
		if(!ListUtil.isEmpty(list)) {
			for(TBLUserRoleEntity entity : list) {
				TBLRoleDefineEntity roleDefine = (TBLRoleDefineEntity)crudService.get(entity.getRoleID(), TBLRoleDefineEntity.class, true);
				UserRoleDetail detail = new UserRoleDetail(entity,roleDefine);
				result.add(detail) ;
			}
		}
		return result;
	}

	
	
}
