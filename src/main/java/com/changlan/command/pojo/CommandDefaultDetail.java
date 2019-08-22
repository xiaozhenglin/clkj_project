package com.changlan.command.pojo;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.StringList;

import com.changlan.command.service.IProtocolService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.service.IPointDefineService;

public class CommandDefaultDetail {
    //一个类别
	private TblPointSendCommandEntity commandDefault; //一条默认指令
	private List<ProtocolInfo> currentDataProtocol;  //当前解析数据的协议
	private TblPointsEntity  point ;  //一个监控点
	private TblCommandCategoryEntity category; // 指令类别
	private List<ContainSendCommands> containSendCommands;
	
	public CommandDefaultDetail(TblPointSendCommandEntity commandDefault) {
		this.commandDefault =commandDefault;
		this.currentDataProtocol = getDataProtocol(commandDefault);
		this.category = getCategory(commandDefault.getCommandCatagoryId());
		this.point = getPoint(commandDefault.getPointId());
		this.containSendCommands = getSendCommands(commandDefault);
//		指标类别this.indicatorCategory = getIndicatorCategory(commandDefault.getIndicatorCategory());
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
		if(!ListUtil.isEmpty(stringToList)) {
			for(String str : stringToList) {
				//System.out.println(str);
				if(StringUtil.isNotEmpty(str)) {
					Integer protocolId = Integer.parseInt(str);
					TblCommandProtocolEntity entity = (TblCommandProtocolEntity)crudService.get(protocolId, TblCommandProtocolEntity.class, true);
					ProtocolInfo info = new ProtocolInfo(entity);
					list.add(info);
				}
			}
		}
		return list;
	}
	
	private List<ContainSendCommands> getSendCommands(TblPointSendCommandEntity commandDefault) {
		List<ContainSendCommands> list = new ArrayList<ContainSendCommands>();
		ICrudService crudService = SpringUtil.getICrudService(); 
		String sendCommandIds = commandDefault.getPreviousSendCommandIds(); 
		if(StringUtil.isNotEmpty(sendCommandIds)) {
			List<String> stringToList = StringUtil.stringToList(sendCommandIds); 
			for(String str : stringToList) {
				Integer sendCommandId = Integer.parseInt(str);
				TblPointSendCommandEntity entity = (TblPointSendCommandEntity)crudService.get(sendCommandId, TblPointSendCommandEntity.class, true);
				ContainSendCommands info = new ContainSendCommands();
				info.setCommandContent(entity.getCommandContent());
				info.setSendCommandId(entity.getSendCommandId());
				info.setCommandName(entity.getCommandName());
				info.setPointId(entity.getPointId());
				info.setProtocolId(entity.getProtocolId());
				if(StringUtil.isNotEmpty(entity.getSystem_start())) {
					info.setSystem_start(entity.getSystem_start());
				}
				if(entity.getIntervalTime()!=null) {
					info.setIntervalTime(entity.getIntervalTime());
				}
				if(StringUtil.isNotEmpty(entity.getIs_controller())) {
					info.setIs_controller(entity.getIs_controller());
				}
				list.add(info);
			}
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

	public List<ContainSendCommands> getContainSendCommands() {
		return containSendCommands;
	}

	public void setContainSendCommands(List<ContainSendCommands> containSendCommands) {
		this.containSendCommands = containSendCommands;
	}

//	public TblIndicatorCategoriesEntity getIndicatorCategory() {
//		return indicatorCategory;
//	}
//
//	public void setIndicatorCategory(TblIndicatorCategoriesEntity indicatorCategory) {
//		this.indicatorCategory = indicatorCategory;
//	}
	
	
	
}
