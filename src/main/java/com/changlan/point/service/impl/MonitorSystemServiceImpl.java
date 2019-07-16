package com.changlan.point.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblMonitorSystemEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.StringUtil;
import com.changlan.point.service.IMonitorSystemService;

@Service
public class MonitorSystemServiceImpl implements IMonitorSystemService{
	
	@Autowired
	ICrudService crudService;


	@Override
	public List<TblMonitorSystemEntity> getAll(TblMonitorSystemEntity entity) {
		List<TblMonitorSystemEntity> all = null;
		Map map = new HashMap();
		if(entity.getChannelId() != null) {
			map.put("channelId", new ParamMatcher(entity.getChannelId()));
		}
		if(StringUtil.isNotEmpty(entity.getName())) {
			map.put("name", new ParamMatcher(entity.getName()));
		}
		if(entity.getMonitorId()  != null) {
			map.put("monitorId", new ParamMatcher(entity.getMonitorId())); 
		}
		all = crudService.findByMoreFiled(TblMonitorSystemEntity.class, map, true);
		//封装公司信息和公司组信息
		return all;
	}

}
