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
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.pojo.UserDetail;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IRoleDefineService;
import com.changlan.user.service.IUserInfoService;
import com.changlan.user.service.IUserRoleService;

@Service
public class UserInfoServiceImpl implements IUserInfoService{

	@Autowired
	ICrudService crudService;
	
	@Autowired
	IUserRoleService  roleService;
	
	@Override
	public Boolean existName(TblAdminUserEntity user) {
		Map map = new HashMap();
		map.put("name", new ParamMatcher(user.getName()));
		List<TblAdminUserEntity> list = crudService.findByMoreFiled(TblAdminUserEntity.class, map, true); 
		String userId = user.getAdminUserId(); 
		if(StringUtil.isEmpty(userId)) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblAdminUserEntity adminUser : list) {
				if(adminUser != null &&  !userId.equalsIgnoreCase(adminUser.getAdminUserId()) ) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<UserDetail> userList(TblAdminUserEntity user) {
		List<UserDetail> userList = new ArrayList<UserDetail>();
		if(user!=null && isSuperAdminUser(user.getAdminUserId()) ) {
			//只有系统管理员可以
			//可以查看所有用户的信息
			List<Object> list = crudService.getAll(TblAdminUserEntity.class, true); 
			for(Object o : list) {
				TblAdminUserEntity userEntity = (TblAdminUserEntity)o;
				UserDetail userDetail = new UserDetail(userEntity);
				userList.add(userDetail);
			}
		}else {
			//否则只能查看自己的
			UserDetail detail = new UserDetail(user);
			userList.add(detail);
		}
		return userList;
	}
	
	public Boolean isSuperAdminUser(String adminUserId) {
		IUserRoleService roleService = SpringUtil.getBean(IUserRoleService.class);
		TBLUserRoleEntity query = new TBLUserRoleEntity();
		query.setUserId(adminUserId); 
		List<UserRoleDetail> list = roleService.getAll(query); 
		for(int i = 0;i<list.size();i++){
			TBLRoleDefineEntity roleDefine = list.get(i).getRoleDefine(); 
			if(roleDefine!=null && roleDefine.getRoleName().indexOf("管理员")>-1) {
				return true;
			}
		}
		return false;
	}

}
