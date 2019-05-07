package com.changlan.command.pojo;

import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;

public class ProtocolInfo {
	private TblCommandProtocolEntity protocol; //一个协议值
//	private TblCommandCategoryEntity category ; //一个指令类别id
	private TblIndicatorValueEntity indicators;//对应一个指标

	
	public ProtocolInfo(TblCommandProtocolEntity entity) {
		this.protocol = entity;
//		this.category = getCategory(entity.getCommandCatagoryId());
		this.indicators = getIndicator(entity.getIndicatorId());
	}
	
	//指令类别
	private TblCommandCategoryEntity getCategory(Integer commandCatagoryId) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		return (TblCommandCategoryEntity)crudService.get(commandCatagoryId, TblCommandCategoryEntity.class, true);
	}

	//根据指标id 获取指标信息
	private  TblIndicatorValueEntity getIndicator(Integer indicator) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		TblIndicatorValueEntity entity = (TblIndicatorValueEntity)crudService.get(indicator, TblIndicatorValueEntity.class, true); 
		return entity;
	}
	
	public ProtocolInfo() {
		super();
	}

	public TblCommandProtocolEntity getProtocol() {
		return protocol;
	}
	public void setProtocol(TblCommandProtocolEntity protocol) {
		this.protocol = protocol;
	}
//	public TblCommandCategoryEntity getCategory() {
//		return category;
//	}
//	public void setCategory(TblCommandCategoryEntity category) {
//		this.category = category;
//	}

	public TblIndicatorValueEntity getIndicators() {
		return indicators;
	}

	public void setIndicators(TblIndicatorValueEntity indicators) {
		this.indicators = indicators;
	}
	
	

	
}
