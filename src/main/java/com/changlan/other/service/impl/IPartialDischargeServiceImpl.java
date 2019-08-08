package com.changlan.other.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.changlan.other.dao.IPartialDischargeDao;
import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.DeviceDataSpecial;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.other.service.IPartialDischargeService;
import com.changlan.point.pojo.PointDataDetail;

@Service
public class IPartialDischargeServiceImpl implements IPartialDischargeService{
	@Autowired
	private IPartialDischargeDao dao;

	@Override
	public Object list(PartialDischargeQuery query) {
		Object list = dao.list(query); 
		return list;
	}

	@Override
	public Object table(PartialDischargeQuery query) {
		Object list = dao.table(query); 
		return list;
	}
	
	@Override
	public Page<DeviceDataSpecial> table(PartialDischargeQuery query,Pageable page) {
		Page<DeviceDataSpecial> list = dao.table(query,page); 
		return list;
	}

	@Override
	public Object channelSettingList(PartialDischargeQuery query) {
		Object list = dao.channelSettingList(query); 
		return list;
	}
}
