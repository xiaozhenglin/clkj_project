package com.changlan.user.pojo;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.user.service.IUserInfoService;

public class UserOperationDetail {
	
	private TblAdminUserEntity user; // 系统用户
	private TblUserOperationEntity entity;
	
	public UserOperationDetail(TblUserOperationEntity entity) { 
		ICrudService crudService = SpringUtil.getBean(ICrudService.class);
		TblAdminUserEntity user = (TblAdminUserEntity)crudService.get(entity.getUserId(), TblAdminUserEntity.class, true);
		this.user = user;
		this.entity = entity;
	}

	public UserOperationDetail() {
		super();
	}


	public TblAdminUserEntity getUser() {
		return user;
	}

	public void setUser(TblAdminUserEntity user) {
		this.user = user;
	}

	public TblUserOperationEntity getEntity() {
		return entity;
	}
	public void setEntity(TblUserOperationEntity entity) {
		this.entity = entity;
	}
	
	
	
}
