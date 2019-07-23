package com.changlan.point.service;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.changlan.other.entity.DeviceDataColl;
import com.changlan.point.pojo.PartDischargeDataDetail;
import com.changlan.point.pojo.PartDischargeDataQuery;
import com.changlan.point.pojo.PartDischargeQuery;


public interface IPartDischargeDataService {
	//获取所有的监控点数据
	List<PartDischargeDataDetail> getAll(DeviceDataColl entity);

	//保存监控点数据
	DeviceDataColl update(DeviceDataColl data);
	
	//分页获取所有监控点数据
	Page<PartDischargeDataDetail> getAll(PartDischargeQuery entity, Pageable page);

	//检点数据图表形式
	List<PartDischargeDataDetail> getTable(Date begin, Date end, Integer indicator, Integer pointId);      

}
