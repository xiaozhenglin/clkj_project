/*
 * Welcome to use the TableGo Tools.

 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.0
 */
package com.changlan.common.entity;
import javax.persistence.GenerationType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TBL_COMMAND_RECORD
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_COMMAND_RECORD")
public class TblCommandRecordEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4929032631946828341L;

    /** commandRecordId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMAND_RECORD_ID", unique = true )
    private Integer commandRecordId;

    /** adminUserId */
    @Column(name = "ADMIN_USER_ID"    )
    private String adminUserId;

    /** commandContent */
    @Column(name = "COMMAND_CONTENT"   )
    private String commandContent;

    /** backContent */
    @Column(name = "BACK_CONTENT"   )
    private String backContent;

    /** sendCOMMANDId */
    @Column(name = "SEND_COMMAND_ID"    )
    private Integer sendCommandId;

    /** pointName */
    @Column(name = "POINT_NAME"  )
    private String pointName;
    
    @Column(name = "POINT_ID"  )
    private Integer pointId;
    
    @Column(name = "COMMAND_CATAGORY_ID"  )
    private Integer commandCatagoryId;
    
    @Column(name = "RECORD_TIME"  )
    private Date recordTime;
    
    @Transient
    private String commandName;
    
    @Transient
    private String commandCatagoryName;
    
	public Integer getCommandRecordId() {
		return commandRecordId;
	}

	public void setCommandRecordId(Integer commandRecordId) {
		this.commandRecordId = commandRecordId;
	}

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getCommandContent() {
		return commandContent;
	}

	public void setCommandContent(String commandContent) {
		this.commandContent = commandContent;
	}

	public String getBackContent() {
		return backContent;
	}

	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}

	public Integer getSendCommandId() {
		return sendCommandId;
	}

	public void setSendCommandId(Integer sendCommandId) {
		this.sendCommandId = sendCommandId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getCommandCatagoryId() {
		return commandCatagoryId;
	}

	public void setCommandCatagoryId(Integer commandCatagoryId) {
		this.commandCatagoryId = commandCatagoryId;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandCatagoryName() {
		return commandCatagoryName;
	}

	public void setCommandCatagoryName(String commandCatagoryName) {
		this.commandCatagoryName = commandCatagoryName;
	}

    
    
}