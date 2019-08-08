package com.changlan.other.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.DeviceDataSpecial;
import com.changlan.other.pojo.PartialDischargeQuery;


public interface IPartialDischargeService {
	//获取列表数据
	Object list(PartialDischargeQuery query);

	//获取表格数据
	Object table(PartialDischargeQuery query); 
	
	Page<DeviceDataSpecial> table(PartialDischargeQuery query ,Pageable page);
	
	//获取通道列表
	Object channelSettingList(PartialDischargeQuery query); 
}
