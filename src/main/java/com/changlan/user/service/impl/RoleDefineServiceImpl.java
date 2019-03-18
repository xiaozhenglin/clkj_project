package com.changlan.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.service.IRoleDefineService;

@Service
public class RoleDefineServiceImpl implements IRoleDefineService{
	
	@Autowired
	ICrudService crudService ;

	@Override
	public Boolean existRoleName(TBLRoleDefineEntity role) {
		Map map = new HashMap();
		map.put("roleName", new ParamMatcher(role.getRoleName()));
		List<TBLRoleDefineEntity> list = crudService.findByMoreFiled(TBLRoleDefineEntity.class, map, true); 
		Integer roleId = role.getRoleId();
		if(roleId!=null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TBLRoleDefineEntity roleDefine : list) {
				if(roleId != null &&  role.getRoleId()!=roleId ) {
					return true;
				}
			}
		}
		return false;
	}

}
