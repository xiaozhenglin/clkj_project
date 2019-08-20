package com.changlan.command.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;

@Service
public class CommandDefaultServiceImpl implements ICommandDefaultService {
	@Autowired
	ICrudService crudService;

	@Override
	public Boolean existName(TblPointSendCommandEntity entity) {
		Map map = new HashMap();
		map.put("commandName", new ParamMatcher(entity.getCommandName()));
		List<TblPointSendCommandEntity> list = crudService.findByMoreFiled(TblPointSendCommandEntity.class, map, true); 
		Integer commandId = entity.getSendCommandId(); 
		if(commandId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblPointSendCommandEntity command : list) {
				if(command != null &&  command.getSendCommandId() != commandId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public TblPointSendCommandEntity save(TblPointSendCommandEntity entity) {
		TblPointSendCommandEntity update = (TblPointSendCommandEntity)crudService.update(entity, true);
		return update;
	}

	@Override
	public List<CommandDefaultDetail> commandList(TblPointSendCommandEntity command) {
		List<CommandDefaultDetail> result = new ArrayList<CommandDefaultDetail>();
		Map map = new HashMap();
		if(command !=null) {
			if(command.getSendCommandId() != null) {
				map.put("sendCommandId", new ParamMatcher(command.getSendCommandId()));
			}
			if(command.getIndicatorCategory()!=null) {
				map.put("indicatorCategory", new ParamMatcher(command.getIndicatorCategory()));
			}
			if(command.getPointId()!=null) {
				map.put("pointId", new ParamMatcher(command.getPointId()));
			}
			if(command.getCommandCatagoryId()!=null) {
				map.put("commandCatagoryId", new ParamMatcher(command.getCommandCatagoryId()));
			}
			if(StringUtil.isNotEmpty(command.getIs_controller())) {
				map.put("is_controller", new ParamMatcher(command.getIs_controller()));
			}
			if(StringUtil.isNotEmpty(command.getSystem_start())) {
				map.put("system_start", new ParamMatcher(command.getSystem_start()));
			}
		}
		List<Object> all = crudService.findByMoreFiled(TblPointSendCommandEntity.class, map, true);
		//封装信息
		for(Object o : all ) {
			TblPointSendCommandEntity entity = (TblPointSendCommandEntity)o;
			CommandDefaultDetail detail = new CommandDefaultDetail(entity);
			result.add(detail);
		}
		return result;
	}
	
	@Override
	public List<TblPointSendCommandEntity> commandRefList(TblPointSendCommandEntity command) {
		List<TblPointSendCommandEntity> result = new ArrayList<TblPointSendCommandEntity>();
		result.clear();
		String sendCommandIds = command.getPreviousSendCommandIds(); 
		if(StringUtil.isNotEmpty(sendCommandIds)) {
			List<String> stringToList = StringUtil.stringToList(sendCommandIds); 
			for(String str : stringToList) {
				Map map = new HashMap();
				if(command !=null) {
					if(StringUtil.isNotEmpty(str)){
						map.put("sendCommandId", new ParamMatcher(Integer.parseInt(str)));
					}
					if(command.getIndicatorCategory()!=null) {
						map.put("indicatorCategory", new ParamMatcher(command.getIndicatorCategory()));
					}
					if(command.getPointId()!=null) {
						map.put("pointId", new ParamMatcher(command.getPointId()));
					}
					if(command.getCommandCatagoryId()!=null) {
						map.put("commandCatagoryId", new ParamMatcher(command.getCommandCatagoryId()));
					}
				}
				List<Object> all = crudService.findByMoreFiled(TblPointSendCommandEntity.class, map, true);
				//封装信息
				for(Object o : all ) {
					TblPointSendCommandEntity entity = (TblPointSendCommandEntity)o;
					result.add(entity);
				}
			}
			
		}
		return result;
	}

}
