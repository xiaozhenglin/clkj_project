package com.changlan.user.pojo;

import com.changlan.common.entity.TBLRoleDefineEntity;
import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;

public class UserRoleDetail {
	private TBLUserRoleEntity userRole; //一个用户角色
	private TBLRoleDefineEntity roleDefine; // 具体的角色信息
	
	public UserRoleDetail(TBLUserRoleEntity userRoleEntity, TBLRoleDefineEntity role) {
		this.userRole = userRoleEntity;
		this.roleDefine = role;
	}

	public UserRoleDetail() {
	}

	public TBLUserRoleEntity getUserRole() {
		return userRole;
	}
	public void setUserRole(TBLUserRoleEntity userRole) {
		this.userRole = userRole;
	}
	public TBLRoleDefineEntity getRoleDefine() {
		return roleDefine;
	}
	public void setRoleDefine(TBLRoleDefineEntity roleDefine) {
		this.roleDefine = roleDefine;
	}
	
}
