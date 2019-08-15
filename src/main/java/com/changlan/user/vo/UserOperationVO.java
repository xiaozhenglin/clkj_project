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
	private String funcName;
	private String isSuccess;
	private String operationType;
	private String curdName;

	public UserOperationVO(UserOperationDetail object) {
		TblUserOperationEntity entity = object.getEntity();
		TblAdminUserEntity user = object.getUser();
		
		this.userId = entity.getUserId();
		if(user!=null) {
			this.userName = user.getName();
		}
		this.userOperationId = entity.getUserOperationId();
		this.recordTime = entity.getRecordTime();
		this.fromIp = entity.getFromIp();
		this.intefaceAddress = entity.getIntefaceAddress();
		this.funcName = entity.getFuncName();
		this.isSuccess = entity.getIsSuccess();
		this.operationType = entity.getOperationType();
		this.curdName = entity.getCurdName();
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

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getCurdName() {
		return curdName;
	}

	public void setCurdName(String curdName) {
		this.curdName = curdName;
	}
		
}
