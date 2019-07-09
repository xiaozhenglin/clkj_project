package com.changlan.command.vo;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;

public class CommandDefaultVO {

	private TblPointSendCommandEntity commandDefault;

	public CommandDefaultVO(CommandDefaultDetail detail) {
		TblPointsEntity point = detail.getPoint();
		TblCommandCategoryEntity category = detail.getCategory();
		this.commandDefault = detail.getCommandDefault();
		this.commandDefault.setPointName(point.getPointName());
		this.commandDefault.setCommandCategoryName(category.getCategoryNmae());
	}

	public TblPointSendCommandEntity getCommandDefault() {
		return commandDefault;
	}

	public void setCommandDefault(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}
	
	

}
