package com.changlan.command.pojo;

import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;

public class CommandRecordDetail extends CommandDefaultDetail{
	
	private TblCommandRecordEntity record; //一条记录
	private TblPointsEntity  point ;  //一个监控点
	
	public CommandRecordDetail(TblCommandRecordEntity record,TblPointSendCommandEntity commandDefault) {
		super(commandDefault);
		this.record = record;
		this.point = getPoint(record.getPointId()); 
	}

	public static  TblPointsEntity getPoint(Integer pointId) {
		ICrudService crudService = SpringUtil.getBean(ICrudService.class);
		TblPointsEntity point = (TblPointsEntity)crudService.get(pointId, TblPointsEntity.class, true);
		return point;
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

	public TblPointsEntity getPoint() {
		return point;
	}

	public void setPoint(TblPointsEntity point) {
		this.point = point;
	}
	
}
