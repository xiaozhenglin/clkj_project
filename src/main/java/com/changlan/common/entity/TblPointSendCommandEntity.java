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

	
	

  
}