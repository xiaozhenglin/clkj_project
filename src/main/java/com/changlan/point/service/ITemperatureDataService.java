package com.changlan.point.service;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblTemperatureDTSDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;

import com.changlan.point.pojo.PointDataDetail;
import com.changlan.point.pojo.TemperatureDataDetail;
import com.changlan.point.pojo.TemperatureDtsDataDetail;
import com.changlan.point.pojo.TemperatureQuery;

public interface ITemperatureDataService {
	//获取所有的监控点数据
	List<TemperatureDataDetail> getAll(TblTemperatureDataEntity entity);

	//保存监控点数据
	TblTemperatureDataEntity update(TblTemperatureDataEntity data);
	
	//分页获取所有监控点数据
	Page<TemperatureDataDetail> getAll(TemperatureQuery entity, Pageable page);

	//检点数据图表形式
	List<TemperatureDataDetail> getTable(Date begin, Date end, Integer indicator, Integer pointId);   
	
	List<TemperatureDtsDataDetail> getDtsTable(Date begin, Date end, Integer indicator, Integer pointId);

}
