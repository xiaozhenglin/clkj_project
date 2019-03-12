package com.changlan.command.pojo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.command.service.IProtocolService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
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
	private List<TblCommandProtocolEntity> currentDataProtocol;  //当前解析数据的协议
	private TblCommandCategoryEntity category; // 指令类别
	private TblPointsEntity  point ;  //一个监控点
	
	public CommandDefaultDetail(TblPointSendCommandEntity commandDefault) {
//		super(commandDefault);
		this.commandDefault =commandDefault;
		this.currentDataProtocol = getDataProtocol(commandDefault);
		this.category = getCategory(commandDefault.getCommandCatagoryId());
		this.point = getPoint(commandDefault.getRegist());
	}
	
	private TblCommandCategoryEntity getCategory(Integer commandCatagoryId) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		TblCommandCategoryEntity result = (TblCommandCategoryEntity)crudService.get(commandCatagoryId, TblCommandCategoryEntity.class, true);
		return result;
	}

	private List<TblCommandProtocolEntity> getDataProtocol(TblPointSendCommandEntity commandDefault) {
		List<TblCommandProtocolEntity> list = new ArrayList<TblCommandProtocolEntity>();
		ICrudService crudService = SpringUtil.getICrudService(); 
		String protocolIds = commandDefault.getProtocolId(); 
		List<String> stringToList = StringUtil.stringToList(protocolIds); 
		for(String str : stringToList) {
			Integer protocolId = Integer.parseInt(str);
			list.add((TblCommandProtocolEntity)crudService.get(protocolId, TblCommandProtocolEntity.class, true));
		}
		return list;
	}
	
	private TblPointsEntity getPoint(String registPackage) {
		IPointDefineService pointDefineService = SpringUtil.getBean(IPointDefineService.class);
		TblPointsEntity byRegistPackage = pointDefineService.getByRegistPackage(registPackage); 
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

	public List<TblCommandProtocolEntity> getCurrentDataProtocol() {
		return currentDataProtocol;
	}

	public void setCurrentDataProtocol(List<TblCommandProtocolEntity> currentDataProtocol) {
		this.currentDataProtocol = currentDataProtocol;
	}

	public TblCommandCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(TblCommandCategoryEntity category) {
		this.category = category;
	}

	public TblPointsEntity getPoint() {
		return point;
	}

	public void setPoint(TblPointsEntity point) {
		this.point = point;
	}
	
	
}
