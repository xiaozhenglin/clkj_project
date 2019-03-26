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

public class CommandProtolDetail implements Serializable{
	private TblCommandProtocolEntity protocol; //一个协议值
	private TblCommandCategoryEntity category; //对应一个协议类别
	private TblIndicatorValueEntity indicators;//对应一个指标
	private TblPointsEntity  point ;  //对应一个监控点
	
	public CommandProtolDetail() {
		super();
	}

	public CommandProtolDetail(TblCommandProtocolEntity entity) { 
		this.protocol = entity;
		this.category = getCategory(entity.getCommandCatagoryId());
		this.indicators = getIndicator(entity.getIndicatorId());
		this.point = getPoint(entity.getPointId());
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
	
	//获取监控点信息
	private TblPointsEntity getPoint(Integer pointId) {
		IPointDefineService pointDefineService = SpringUtil.getBean(IPointDefineService.class);
		TblPointsEntity byRegistPackage = pointDefineService.getByRegistPackageOrId(pointId, null);
		return byRegistPackage;
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

	public TblIndicatorValueEntity getIndicators() {
		return indicators;
	}

	public void setIndicators(TblIndicatorValueEntity indicators) {
		this.indicators = indicators;
	}

	public TblPointsEntity getPoint() {
		return point;
	}

	public void setPoint(TblPointsEntity point) {
		this.point = point;
	}
	
	
	

	
}
