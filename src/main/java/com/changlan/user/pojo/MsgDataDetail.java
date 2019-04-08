package com.changlan.user.pojo;

import java.io.Serializable;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblMsgDataEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.user.service.IUserInfoService;

public class MsgDataDetail implements Serializable{
	
	private TblMsgDataEntity data; //消息记录
	private TblAdminUserEntity user; //用户信息
	
	public MsgDataDetail(TblMsgDataEntity data) {
		super();
		this.data = data;
		this.user = getUserBaseInfo(data.getAdminUserId());
	}
	
	private TblAdminUserEntity getUserBaseInfo(String adminUserId) {
		if(StringUtil.isNotEmpty(adminUserId)) { 
			ICrudService crudService = SpringUtil.getICrudService();
			TblAdminUserEntity user = (TblAdminUserEntity)crudService.get(adminUserId, TblAdminUserEntity.class, true);
			return user;
		}
		return null;
	}

	public MsgDataDetail() {
		super();
	}

	public TblMsgDataEntity getData() {
		return data;
	}
	public void setData(TblMsgDataEntity data) {
		this.data = data;
	}
	public TblAdminUserEntity getUser() {
		return user;
	}
	public void setUser(TblAdminUserEntity user) {
		this.user = user;
	}
	
	
}
