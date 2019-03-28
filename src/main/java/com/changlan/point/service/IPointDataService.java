package com.changlan.point.service;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.point.pojo.PointDataDetail;

public interface IPointDataService {
	//获取所有的监控点数据
	List<PointDataDetail> getAll(TblPoinDataEntity entity);

	//保存监控点数据
	TblPoinDataEntity update(TblPoinDataEntity data);
	
	//分页获取所有监控点数据
	Page<PointDataDetail> getAll(TblPoinDataEntity entity, Pageable page);

	//检点数据图表形式
	List<PointDataDetail> getTable(Date begin, Date end, Integer indicator, Integer pointId);      

}
