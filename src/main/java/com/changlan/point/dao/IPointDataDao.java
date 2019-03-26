package com.changlan.point.dao;

import java.util.Date;
import java.util.List;

import com.changlan.common.entity.TblPoinDataEntity;

public interface IPointDataDao {
	//获取上次的监控点的指标值
	TblPoinDataEntity getThePenultimateData(Integer pointId,Integer indicatorId);
	
	//获取表格数据
	List<TblPoinDataEntity> getTableData(Date begin, Date end, Integer categroryId); 
}
