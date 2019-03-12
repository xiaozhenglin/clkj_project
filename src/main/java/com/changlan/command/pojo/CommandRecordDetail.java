package com.changlan.command.pojo;

import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.service.IPointDefineService;

public class CommandRecordDetail extends CommandDefaultDetail{
	
	private TblCommandRecordEntity record; //一条记录
	
	public CommandRecordDetail(TblCommandRecordEntity record,TblPointSendCommandEntity commandDefault) {
		super(commandDefault);
		this.record = record;
//		this.point = getPoint(record.getPointRegistPackage()); 
	}

	public CommandRecordDetail() {
		super();
	}

	public TblCommandRecordEntity getRecord() {
		return record;
	}

	public void setRecord(TblCommandRecordEntity record) {
		this.record = record;
	}

}
