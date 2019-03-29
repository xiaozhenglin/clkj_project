package com.changlan.other.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.other.dao.IPartialDischargeDao;
import com.changlan.other.pojo.PartialDischargeQuery;
import com.changlan.other.service.IPartialDischargeService;

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
	public Object channelSettingList() {
		Object list = dao.channelSettingList(); 
		return list;
	}
}
