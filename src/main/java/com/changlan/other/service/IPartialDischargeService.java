package com.changlan.other.service;

import java.util.Date;

import com.changlan.other.pojo.PartialDischargeQuery;

public interface IPartialDischargeService {
	//获取列表数据
	Object list(PartialDischargeQuery query);

	//获取表格数据
	Object table(PartialDischargeQuery query); 
	
	//获取通道列表
	Object channelSettingList(PartialDischargeQuery query); 
}
