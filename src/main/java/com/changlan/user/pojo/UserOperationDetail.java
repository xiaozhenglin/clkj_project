package com.changlan.user.pojo;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserOperationEntity;

public class UserOperationDetail {
	
	private TblAdminUserEntity user;
	private TblUserOperationEntity entity;
	
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
