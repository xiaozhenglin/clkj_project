package com.changlan.point.dao;

import java.util.Date;
import java.util.List;

import com.changlan.common.entity.TblTemperatureDTSDataEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.point.pojo.TemperatureDtsQuery;

public interface ITemperatureDataDao {
	//获取上次的监控点的指标值
	TblTemperatureDataEntity getThePenultimateData(Integer pointId,Integer indicatorId);
	
	//获取表格数据
	List<TblTemperatureDataEntity> getTableData(Date begin, Date end,Integer indicators, Integer pointId); 
	
	List<TblTemperatureDTSDataEntity> getDtsTableData(Date begin, Date end,Integer indicatorId,Integer pointId,Integer refPointDataId);

	List<TblTemperatureDTSDataEntity> Table(TemperatureDtsQuery query);
}
