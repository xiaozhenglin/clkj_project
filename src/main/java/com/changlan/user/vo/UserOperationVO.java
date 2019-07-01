package com.changlan.user.vo;

import java.util.Date;

import javax.persistence.Column;

import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblUserOperationEntity;
import com.changlan.user.pojo.UserDetail;
import com.changlan.user.pojo.UserOperationDetail;

public class UserOperationVO {

	private String userId;
	private String userName;
	private Integer userOperationId;
	private Date recordTime;
	private String fromIp;
	private String intefaceAddress; 

	public UserOperationVO(UserOperationDetail object) {
		TblUserOperationEntity entity = object.getEntity();
		TblAdminUserEntity user = object.getUser();
		
		this.userId = entity.getUserId();
		this.userName = user.getName();
		this.userOperationId = entity.getUserOperationId();
		this.recordTime = entity.getRecordTime();
		this.fromIp = entity.getFromIp();
		this.intefaceAddress = entity.getIntefaceAddress();
	}

	public UserOperationVO() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserOperationId() {
		return userOperationId;
	}

	public void setUserOperationId(Integer userOperationId) {
		this.userOperationId = userOperationId;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public String getIntefaceAddress() {
		return intefaceAddress;
	}

	public void setIntefaceAddress(String intefaceAddress) {
		this.intefaceAddress = intefaceAddress;
	}
	
	
}
