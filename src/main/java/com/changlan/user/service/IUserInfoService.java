package com.changlan.user.service;

import java.util.List;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.user.pojo.UserDetail;

public interface IUserInfoService {

	//判断用户名是否已经存在
	Boolean existName(TblAdminUserEntity user);

	List<UserDetail> userList(TblAdminUserEntity user);  

}
