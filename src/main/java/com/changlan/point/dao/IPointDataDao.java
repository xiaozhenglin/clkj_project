package com.changlan.point.dao;

import com.changlan.common.entity.TblPoinDataEntity;

public interface IPointDataDao {
	//获取上次的监控点的指标值
	TblPoinDataEntity getThePenultimateData(Integer pointId,Integer indicatorId);
}
