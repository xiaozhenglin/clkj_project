package com.changlan.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TBL_USER_OPERATION")
public class TblUserOperationEntity implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_OPERATION_ID", unique = true )
	private Integer userOperationId;
	
    @Column(name = "RECORD_TIME")
	private Date recordTime;
	
    @Column(name = "FROM_IP" )
	private String fromIp;
	
    @Column(name = "USER_ID" )
	private String userId	;
	
    @Column(name = "INTEFACE_ADDRESS")
	private String intefaceAddress;
    
    @Column(name = "FUNC_NAME")
    private String funcName;
    
    @Column(name = "IS_SUCCESS")
    private String isSuccess;
    
    @Column(name = "OPERATION_TYPE")
    private String operationType;
    
    @Column(name = "CRUD_NAME")
    private String curdName;
    
    
	public TblUserOperationEntity(Integer userOperationId, Date recordTime, String fromIp, String userId,
			String intefaceAddress) {
		super();
		this.userOperationId = userOperationId;
		this.recordTime = recordTime;
		this.fromIp = fromIp;
		this.userId = userId;
		this.intefaceAddress = intefaceAddress;
	}
		
	
	public TblUserOperationEntity(Integer userOperationId, Date recordTime, String fromIp, String userId,
			String intefaceAddress, String funcName, String isSuccess, String operationType, String curdName) {
		super();
		this.userOperationId = userOperationId;
		this.recordTime = recordTime;
		this.fromIp = fromIp;
		this.userId = userId;
		this.intefaceAddress = intefaceAddress;
		this.funcName = funcName;
		this.isSuccess = isSuccess;
		this.operationType = operationType;
		this.curdName = curdName;
	}


	public TblUserOperationEntity() {
		super();
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
