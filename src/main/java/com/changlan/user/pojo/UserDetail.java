package com.changlan.user.pojo;

import com.changlan.common.entity.TBLUserRoleEntity;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserGroupEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.user.service.IUserGoupService;
import com.changlan.user.service.IUserRoleService;

public class UserDetail extends UserRoleDetail{
	private TblAdminUserEntity user; // 系统用户
	private TblUserGroupEntity userGroup; //用户所属组信息
	
	public UserDetail(TblAdminUserEntity user) {
		super();
		this.user = user;
		IUserRoleService userRoleService = SpringUtil.getBean(IUserRoleService.class);
		UserRoleDetail all = userRoleService.getOne(user.getAdminUserId()); 
		if(all !=null ) {
			super.setRoleDefine(all.getRoleDefine());
			super.setUserRole(all.getUserRole()); 
		}
		this.userGroup = getGroup(user.getUserGroupId());
	}
	
	private TblUserGroupEntity getGroup(Integer userGroupId) {
		if(userGroupId!=null) {
			ICrudService crudService = SpringUtil.getICrudService();
			return (TblUserGroupEntity)crudService.get(userGroupId, TblUserGroupEntity.class, true);
		}
		return null;
	}

	public TblAdminUserEntity getUser() {
		return user;
	}
	public void setUser(TblAdminUserEntity user) {
		this.user = user;
	}

	public TblUserGroupEntity getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(TblUserGroupEntity userGroup) {
		this.userGroup = userGroup;
	}

	
	
}
