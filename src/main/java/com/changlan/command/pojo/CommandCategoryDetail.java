package com.changlan.command.pojo;

import java.util.List;

import com.changlan.command.service.IProtocolService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;

public class CommandCategoryDetail {
	
	private TblCommandCategoryEntity category ; //一个类别id
	private List<CommandProtolDetail> protocols; //多个协议
	
	public CommandCategoryDetail(TblCommandCategoryEntity entity) {
		this.category = entity;
		this.protocols =  getProtocols(entity.getCommandCatagoryId());
	}

	public CommandCategoryDetail(TblPointSendCommandEntity commandDefault) {
		ICrudService crudService = SpringUtil.getBean(ICrudService.class);
		TblCommandCategoryEntity category = (TblCommandCategoryEntity)crudService.get(commandDefault.getCommandCatagoryId(), TblCommandCategoryEntity.class, true);
		this.category = category;
		this.protocols =  getProtocols(category.getCommandCatagoryId());
	}
	
	public CommandCategoryDetail() {
		
	}

	public static  List<CommandProtolDetail> getProtocols(Integer commandCatagoryId) {
		IProtocolService protocolService = SpringUtil.getBean(IProtocolService.class);
		TblCommandProtocolEntity protocol = new TblCommandProtocolEntity();
		protocol.setCommandCatagoryId(commandCatagoryId); 
		List<CommandProtolDetail> protocolList = protocolService.protocolList(protocol); 
		return protocolList;
	}

	public TblCommandCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(TblCommandCategoryEntity category) {
		this.category = category;
	}

	public List<CommandProtolDetail> getProtocols() {
		return protocols;
	}

	public void setProtocols(List<CommandProtolDetail> protocols) {
		this.protocols = protocols;
	}
	
}
