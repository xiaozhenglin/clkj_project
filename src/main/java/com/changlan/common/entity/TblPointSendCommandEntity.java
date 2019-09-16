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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TBL_POINT_SEND_COMMAND
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_POINT_SEND_COMMAND")
public class TblPointSendCommandEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8533489228781829605L;

    /** sendCOMMANDId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SEND_COMMAND_ID", unique = true )
    private Integer sendCommandId ;

    /** COMMANDContent */
    @Column(name = "COMMAND_CONTENT"   )
    private String commandContent;

    /** COMMANDCatagoryId */
    @Column(name = "COMMAND_CATAGORY_ID"    )
    private Integer commandCatagoryId;//指令解析类别id

    /** COMMANDName */
    @Column(name = "COMMAND_NAME"  )
    private String commandName;

    /** remark */
    @Column(name = "REMARK"  )
    private String remark;
    
    @Column(name = "PROTOCOL_ID"  )
    private String protocolId;
    
    @Column(name = "INTERVAL_TIME"  )
    private Integer intervalTime;
    
    @Column(name = "POINT_ID"  )
    private Integer pointId; //监控点id
    
    @Column(name = "INDICATOR_CATEGORY"  )
    private Integer indicatorCategory; //指标类别
    
    /** COMMANDContent */
    @Column(name = "PREVIOUS_COMMAND_CONTENT"   )
    private String previousCommandContent;
    
    /** COMMANDName */
    @Column(name = "PREVIOUS_COMMAND_NAME"  )
    private String previousCommandName;
    
    @Column(name = "PREVIOUS_SEND_COMMAND_IDS"  )
    private String previousSendCommandIds;
  
    
    @Column(name = "SYSTEM_START"  )
    private String system_start;
    
    @Column(name = "IS_CONTROLLER"  )
    private String is_controller;
    
    @Column(name = "PREVIOUS_VISUAL_TYPE"  )
    private String previousVisualType;
    
    @Column(name = "EXCEPTION_COMMAND_IDS"  )
    private String exceptionCommandIds;
    
    @Column(name = "EXCEPTION_VALUE"  )
    private String exceptionValue;
    
    @Transient
   	private String  pointName;
    @Transient
   	private String  commandCategoryName;

	public Integer getSendCommandId() {
		return sendCommandId;
	}

	public void setSendCommandId(Integer sendCommandId) {
		this.sendCommandId = sendCommandId;
	}

	public String getCommandContent() {
		return commandContent;
	}

	public void setCommandContent(String commandContent) {
		this.commandContent = commandContent;
	}

	public Integer getCommandCatagoryId() {
		return commandCatagoryId;
	}

	public void setCommandCatagoryId(Integer commandCatagoryId) {
		this.commandCatagoryId = commandCatagoryId;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getIndicatorCategory() {
		return indicatorCategory;
	}

	public void setIndicatorCategory(Integer indicatorCategory) {
		this.indicatorCategory = indicatorCategory;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getCommandCategoryName() {
		return commandCategoryName;
	}

	public void setCommandCategoryName(String commandCategoryName) {
		this.commandCategoryName = commandCategoryName;
	}

	public String getPreviousCommandContent() {
		return previousCommandContent;
	}

	public void setPreviousCommandContent(String previousCommandContent) {
		this.previousCommandContent = previousCommandContent;
	}

	public String getPreviousCommandName() {
		return previousCommandName;
	}

	public void setPreviousCommandName(String previousCommandName) {
		this.previousCommandName = previousCommandName;
	}

	public String getPreviousSendCommandIds() {
		return previousSendCommandIds;
	}

	public void setPreviousSendCommandIds(String previousSendCommandIds) {
		this.previousSendCommandIds = previousSendCommandIds;
	}

	public String getSystem_start() {
		return system_start;
	}

	public void setSystem_start(String system_start) {
		this.system_start = system_start;
	}

	public String getIs_controller() {
		return is_controller;
	}

	public void setIs_controller(String is_controller) {
		this.is_controller = is_controller;
	}

	public String getPreviousVisualType() {
		return previousVisualType;
	}

	public void setPreviousVisualType(String previousVisualType) {
		this.previousVisualType = previousVisualType;
	}

	public String getExceptionCommandIds() {
		return exceptionCommandIds;
	}

	public void setExceptionCommandIds(String exceptionCommandIds) {
		this.exceptionCommandIds = exceptionCommandIds;
	}

	public String getExceptionValue() {
		return exceptionValue;
	}

	public void setExceptionValue(String exceptionValue) {
		this.exceptionValue = exceptionValue;
	}
	    
}