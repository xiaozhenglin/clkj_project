package com.changlan.user.service;

import java.util.List;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserFunctionEntity;
import com.changlan.user.pojo.UserFunctionInfo;

public interface IUserFunctionService {
	
	//查询单个用户的权限信息
	UserFunctionInfo findOne(TblAdminUserEntity user);  
	
	//查询所有用户的权限信息
	List<UserFunctionInfo> findALLPersions();

	//添加或者修改用户的权限
	Boolean merge(String adminUserId, List<String> functionIds);  
}
