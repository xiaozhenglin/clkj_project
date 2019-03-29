package com.changlan.other.dao;

import java.util.Date;
import java.util.List;

import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.other.pojo.PartialDischargeQuery;

public interface IPartialDischargeDao {

	Object list(PartialDischargeQuery query);  
	Object table(PartialDischargeQuery query);
	Object channelSettingList(PartialDischargeQuery query);  

}
