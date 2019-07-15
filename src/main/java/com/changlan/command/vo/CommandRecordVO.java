package com.changlan.command.vo;

import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.user.pojo.LoginUser;

public class CommandRecordVO {
	
	private TblCommandRecordEntity record;
	

	public CommandRecordVO(CommandRecordDetail detail) {
		this.record = detail.getRecord();
		TblPointSendCommandEntity commandDefault = detail.getCommandDefault();
		TblCommandCategoryEntity category = detail.getCategory();
		this.record.setCommandName(commandDefault.getCommandName());
		this.record.setCommandCatagoryName(category.getCategoryNmae());
		this.record.setPointName(detail.getRecord().getPointName());
		/*
		 * TblAdminUserEntity currentUser = LoginUser.getCurrentUser();
		 * if(currentUser!=null) {
		 * this.record.setAdminUserId(LoginUser.getCurrentUser().getAdminUserId());//
		 * 记录操作人 }
		 */
	}

	public TblCommandRecordEntity getRecord() {
		return record;
	}

	public void setRecord(TblCommandRecordEntity record) {
		this.record = record;
	}

	
			
}
