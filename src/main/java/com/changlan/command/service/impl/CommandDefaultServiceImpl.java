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
	public List<CommandDefaultDetail> commandList(Integer id,Integer indicatorCategory) {
		List<CommandDefaultDetail> result = new ArrayList<CommandDefaultDetail>();
		Map map = new HashMap();
		List<Object> all = new ArrayList<Object>();
		if(id != null) {
			map.put("sendCommandId", new ParamMatcher(id));
		}
		if(indicatorCategory!=null) {
			map.put("indicatorCategory", new ParamMatcher(indicatorCategory));
		}
		all = crudService.findByMoreFiled(TblPointSendCommandEntity.class, map, true);
		//封装信息
		for(Object o : all ) {
			TblPointSendCommandEntity command = (TblPointSendCommandEntity)o;
			CommandDefaultDetail detail = new CommandDefaultDetail(command);
			result.add(detail);
		}
		return result;
	}

}
