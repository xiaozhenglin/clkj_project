package com.changlan.command.vo;

import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;

public class CommandRecordVO {
	
	private TblCommandRecordEntity record;

	public CommandRecordVO(CommandRecordDetail detail) {
		this.record = detail.getRecord();
		TblPointSendCommandEntity commandDefault = detail.getCommandDefault();
		TblCommandCategoryEntity category = detail.getCategory();
		this.record.setCommandName(commandDefault.getCommandName());
		this.record.setCommandCatagoryName(category.getCategoryNmae());
	}

	public TblCommandRecordEntity getRecord() {
		return record;
	}

	public void setRecord(TblCommandRecordEntity record) {
		this.record = record;
	}
	
	
	
}
