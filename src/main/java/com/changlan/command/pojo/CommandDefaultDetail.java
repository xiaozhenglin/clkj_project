package com.changlan.command.pojo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.command.service.IProtocolService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.service.IPointDefineService;

public class CommandDefaultDetail {
//	extends CommandCategoryDetail
    //一个类别
	private TblPointSendCommandEntity commandDefault; //一条默认指令
	private List<ProtocolInfo> currentDataProtocol;  //当前解析数据的协议
	private TblPointsEntity  point ;  //一个监控点
//	private TblIndicatorCategoriesEntity indicatorCategory; //指标类别
//	private TblCommandCategoryEntity category; // 指令类别
	
	public CommandDefaultDetail(TblPointSendCommandEntity commandDefault) {
//		super(commandDefault);
		this.commandDefault =commandDefault;
		this.currentDataProtocol = getDataProtocol(commandDefault);
//		this.category = getCategory(commandDefault.getCommandCatagoryId());
		this.point = getPoint(commandDefault.getPointId());
//		this.indicatorCategory = getIndicatorCategory(commandDefault.getIndicatorCategory());
	}
	
	private TblIndicatorCategoriesEntity getIndicatorCategory(Integer indicatorCategory) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		TblIndicatorCategoriesEntity result = (TblIndicatorCategoriesEntity)crudService.get(indicatorCategory, TblIndicatorCategoriesEntity.class, true);
		return result;
	}

	private List<ProtocolInfo> getDataProtocol(TblPointSendCommandEntity commandDefault) {
		List<ProtocolInfo> list = new ArrayList<ProtocolInfo>();
		ICrudService crudService = SpringUtil.getICrudService(); 
		String protocolIds = commandDefault.getProtocolId(); 
		List<String> stringToList = StringUtil.stringToList(protocolIds); 
		for(String str : stringToList) {
			Integer protocolId = Integer.parseInt(str);
			TblCommandProtocolEntity entity = (TblCommandProtocolEntity)crudService.get(protocolId, TblCommandProtocolEntity.class, true);
			ProtocolInfo info = new ProtocolInfo(entity);
			list.add(info);
		}
		return list;
	}

	private TblCommandCategoryEntity getCategory(Integer commandCatagoryId) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		TblCommandCategoryEntity result = (TblCommandCategoryEntity)crudService.get(commandCatagoryId, TblCommandCategoryEntity.class, true);
		return result;
	}

//	private List<TblCommandProtocolEntity> getDataProtocol(TblPointSendCommandEntity commandDefault) {
//		List<TblCommandProtocolEntity> list = new ArrayList<TblCommandProtocolEntity>();
//		ICrudService crudService = SpringUtil.getICrudService(); 
//		String protocolIds = commandDefault.getProtocolId(); 
//		List<String> stringToList = StringUtil.stringToList(protocolIds); 
//		for(String str : stringToList) {
//			Integer protocolId = Integer.parseInt(str);
//			list.add((TblCommandProtocolEntity)crudService.get(protocolId, TblCommandProtocolEntity.class, true));
//		}
//		return list;
//	}
	
	private TblPointsEntity getPoint(Integer pointId) {
		IPointDefineService pointDefineService = SpringUtil.getBean(IPointDefineService.class);
		TblPointsEntity byRegistPackage = pointDefineService.getByRegistPackageOrId(pointId, null);
		return byRegistPackage;
	}


	public CommandDefaultDetail() {
		super();
	}


	public TblPointSendCommandEntity getCommandDefault() {
		return commandDefault;
	}
	public void setCommandDefault(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}

//	public List<TblCommandProtocolEntity> getCurrentDataProtocol() {
//		return currentDataProtocol;
//	}
//
//	public void setCurrentDataProtocol(List<TblCommandProtocolEntity> currentDataProtocol) {
//		this.currentDataProtocol = currentDataProtocol;
//	}

	public List<ProtocolInfo> getCurrentDataProtocol() {
		return currentDataProtocol;
	}

	public void setCurrentDataProtocol(List<ProtocolInfo> currentDataProtocol) {
		this.currentDataProtocol = currentDataProtocol;
	}

//	public TblCommandCategoryEntity getCategory() {
//		return category;
//	}
//
//	public void setCategory(TblCommandCategoryEntity category) {
//		this.category = category;
//	}

	public TblPointsEntity getPoint() {
		return point;
	}

	public void setPoint(TblPointsEntity point) {
		this.point = point;
	}

//	public TblIndicatorCategoriesEntity getIndicatorCategory() {
//		return indicatorCategory;
//	}
//
//	public void setIndicatorCategory(TblIndicatorCategoriesEntity indicatorCategory) {
//		this.indicatorCategory = indicatorCategory;
//	}
	
	
}
