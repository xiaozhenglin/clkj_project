package com.changlan.command.vo;

import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPointsEntity;

public class CommandProtolVO {
	
	private TblCommandProtocolEntity protocol;

	public CommandProtolVO(CommandProtolDetail detail) {
		TblIndicatorValueEntity indicators = detail.getIndicators();
		TblPointsEntity point = detail.getPoint();
		TblCommandCategoryEntity category = detail.getCategory();
		this.protocol = detail.getProtocol();
		this.protocol.setPointName(point.getPointName());
		this.protocol.setCommandCategoryName(category.getCategoryNmae());
		this.protocol.setIndicatorName(indicators.getName());
		this.protocol.setUnit(indicators.getUnit());
	}

	public TblCommandProtocolEntity getProtocol() {
		return protocol;
	}

	public void setProtocol(TblCommandProtocolEntity protocol) {
		this.protocol = protocol;
	}
	
	
	
}
