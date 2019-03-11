package com.changlan.user.pojo;

import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.user.service.IUserRoleService;

public class UserDetail extends UserRoleDetail{
	private TblAdminUserEntity user; // 系统用户
	
	public UserDetail(TblAdminUserEntity user) {
		super();
		this.user = user;
		IUserRoleService userRoleService = SpringUtil.getBean(IUserRoleService.class);
		UserRoleDetail all = userRoleService.getOne(user.getAdminUserId()); 
		if(all !=null ) {
			super.setRoleDefine(all.getRoleDefine());
			super.setUserRole(all.getUserRole()); 
		}
	}
	
	public TblAdminUserEntity getUser() {
		return user;
	}
	public void setUser(TblAdminUserEntity user) {
		this.user = user;
	}

	
	
}
