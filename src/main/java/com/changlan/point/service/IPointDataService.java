package com.changlan.point.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.point.pojo.PointDataDetail;

public interface IPointDataService {
	
	List<PointDataDetail> getAll(TblPoinDataEntity entity);

	TblPoinDataEntity update(TblPoinDataEntity data);
	//分页获取所有监控点数据
	Page<PointDataDetail> getAll(TblPoinDataEntity entity, Pageable page);

	//检点数据图表形式
	List getTable(Date begin, Date end, Integer categroryId);    

}
