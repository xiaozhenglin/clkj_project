package com.changlan.user.service;

import java.util.List;

import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.user.pojo.UserRoleDetail;

public interface IUserRoleService {
	
//	UserRoleDetail getOne(String adminUserId);

	Boolean existRole(TBLUserRoleEntity role);

	List<UserRoleDetail> getAll(TBLUserRoleEntity role);    
}
