package com.changlan.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.pojo.UserDetail;
import com.changlan.user.pojo.UserRoleDetail;
import com.changlan.user.service.IUserInfoService;

@Service
public class UserInfoServiceImpl implements IUserInfoService{

	@Autowired
	ICrudService crudService;
	
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
		UserDetail detail = new UserDetail(user);
		//只有系统管理员可以
		if(detail!=null && detail.getRoleDefine().getRoleId() == 1) {
			//可以查看所有用户的信息
			List<Object> list = crudService.getAll(TblAdminUserEntity.class, true); 
			for(Object o : list) {
				TblAdminUserEntity userEntity = (TblAdminUserEntity)o;
				UserDetail userDetail = new UserDetail(userEntity);
				userList.add(userDetail);
			}
		}else {
			//否则只能查看自己的
			userList.add(detail);
		}
		return userList;
	}

}
