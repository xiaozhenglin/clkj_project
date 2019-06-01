package com.changlan.point.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.common.entity.TblCompanyChannelEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.service.IChannelService;

@Service
public class ChannelServiceImpl implements IChannelService{

	@Autowired
	ICrudService crudService;

	@Override
	public Boolean existChannelName(TblCompanyChannelEntity entity) {
		Map map = new HashMap();
		map.put("name", new ParamMatcher(entity.getName()));
		List<TblCompanyChannelEntity> list = crudService.findByMoreFiled(TblCompanyChannelEntity.class, map, true); 
		Integer channelId = entity.getChannelId(); 
		if(channelId == null) {
			//添加
			if(!ListUtil.isEmpty(list)) {
				return true;
			}
		}else {
			//修改
			for(TblCompanyChannelEntity channel : list) {
				if(channel != null &&  channel.getChannelId() != channelId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<TblCompanyChannelEntity> getAllChannel(TblCompanyChannelEntity entity) {
		List<TblCompanyChannelEntity> all = null;
		Map map = new HashMap();
		if(entity.getChannelId() != null) {
			map.put("channelId", new ParamMatcher(entity.getChannelId()));
		}
		if(StringUtil.isNotEmpty(entity.getName())) {
			map.put("name", new ParamMatcher(entity.getName()));
		}
		if(entity.getCompanyId()  != null) {
			map.put("companyId", new ParamMatcher(entity.getCompanyId())); 
		}
		all = crudService.findByMoreFiled(TblCompanyChannelEntity.class, map, true);
		//封装公司信息和公司组信息
		return all;
	}
	
	
}
