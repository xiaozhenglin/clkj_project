package com.changlan.command.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.command.service.IProtocolService;
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;

@Service
public class ProtocolServiceImpl implements IProtocolService{
	
	@Autowired
	ICrudService crudService;

	@Override
	public Boolean existName(TblCommandProtocolEntity entity) {
		Map map = new HashMap();
		map.put("dataType", new ParamMatcher(entity.getDataType()));
		List<TblCommandProtocolEntity> list = crudService.findByMoreFiled(TblCommandProtocolEntity.class, map, true); 
		Integer protocolId = entity.getProtocolId(); 
		if(protocolId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblCommandProtocolEntity protol : list) {
				if(protol != null &&  protol.getProtocolId() != protocolId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public TblCommandProtocolEntity save(TblCommandProtocolEntity entity) {
		TblCommandProtocolEntity update = (TblCommandProtocolEntity)crudService.update(entity, true);
		return update;
	}

	@Override
	public List<CommandProtolDetail> protocolList(TblCommandProtocolEntity protocol) {
		List<CommandProtolDetail> result = new ArrayList<CommandProtolDetail>();
		List<Object> all = new ArrayList<Object>();
		Map map = new HashMap();
		if(protocol.getCommandCatagoryId() != null) {
			map.put("commandCatagoryId", new ParamMatcher(protocol.getCommandCatagoryId()));
		}
		if(protocol.getProtocolId() != null) {
			map.put("protocolId", new ParamMatcher(protocol.getProtocolId()));
		}
		if(protocol.getPointId()!=null) {
			map.put("pointId", new ParamMatcher(protocol.getPointId()));
		}
		if(protocol.getIndicatorId()!=null) {
			map.put("indicatorId", new ParamMatcher(protocol.getIndicatorId()));
		}
		all = crudService.findByMoreFiled(TblCommandProtocolEntity.class, map, true);
		//封装信息
		for(Object o : all ) {
			TblCommandProtocolEntity entity = (TblCommandProtocolEntity)o;
			CommandProtolDetail detail =  new CommandProtolDetail(entity);
			result.add(detail);
		}
		return result;
	}

}
