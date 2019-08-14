package com.changlan.command.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.changlan.command.service.ICommandCategoryService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.service.IPointDefineService;

public class ContainSendCommands implements Serializable{
	private Integer sendCommandId ;
	private String commandContent;
	private String commandName;
	private Integer pointId;
	private String system_start;
	private String is_controller;
	private Integer intervalTime;
	private String protocolId;
	private String msg;
						
	public ContainSendCommands(Integer sendCommandId, String commandContent, String commandName, Integer pointId,
			String system_start, String is_controller, Integer intervalTime, String msg) {
		super();
		this.sendCommandId = sendCommandId;
		this.commandContent = commandContent;
		this.commandName = commandName;
		this.pointId = pointId;
		this.system_start = system_start;
		this.is_controller = is_controller;
		this.intervalTime = intervalTime;
		this.msg = msg;
	}

	public ContainSendCommands() {
		super();
	}

	
	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

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
	public String getCommandName() {
		return commandName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
	
	
}
