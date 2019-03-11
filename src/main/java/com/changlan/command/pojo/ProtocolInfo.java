package com.changlan.command.pojo;

import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;

public class ProtocolInfo {
	private TblCommandProtocolEntity protocol; //一个协议值
	private TblCommandCategoryEntity category ; //一个类别id
	
	
	public ProtocolInfo(TblCommandProtocolEntity entity, TblCommandCategoryEntity category) {
		this.protocol = entity;
		this.category = category;
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
	public TblCommandCategoryEntity getCategory() {
		return category;
	}
	public void setCategory(TblCommandCategoryEntity category) {
		this.category = category;
	}
	
	

	
}
