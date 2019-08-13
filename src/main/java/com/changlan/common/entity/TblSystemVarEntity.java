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
@Table(name="TBL_SYSTEM_VAR")
public class TblSystemVarEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SYSTEM_ID" )
	private Integer systemId;	
	
    @Column(name = "SYSTEM_NAME" )
	private String  systemName; //系统名称
	
    @Column(name = "SYSTEM_VALUE" )
	private String systemValue; //值
	
    @Column(name = "SYSTEM_CODE" )
	private String systemCode; //变量代码

	@Column(name="RECORD_USER")
	private String recordUser; //用户id
	
	@Column(name="DATA_TYPE")
	private String dataType;//数据类型
	
	@Column(name="DEFAULT_VALUE")
	private String defaultValue;//数据类型

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemValue() {
		return systemValue;
	}

	public void setSystemValue(String systemValue) {
		this.systemValue = systemValue;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}			
    
	
}
