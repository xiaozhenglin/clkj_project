package com.changlan.other.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.other.entity.DeviceData;
import com.changlan.other.entity.DeviceDataSpecial;
import com.changlan.other.entity.PartialDischargeEntity;
import com.changlan.other.pojo.PartialDischargeQuery;

public interface IPartialDischargeDao {

	Object list(PartialDischargeQuery query);  
	Object table(PartialDischargeQuery query);
	Object channelSettingList(PartialDischargeQuery query); 
	Page<DeviceDataSpecial> table(PartialDischargeQuery query,Pageable page);

}
