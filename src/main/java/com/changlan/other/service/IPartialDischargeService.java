package com.changlan.other.service;

import java.util.Date;

import com.changlan.other.pojo.PartialDischargeQuery;

public interface IPartialDischargeService {
	Object list(PartialDischargeQuery query);

	Object table(PartialDischargeQuery query); 
	
	Object channelSettingList();
}
