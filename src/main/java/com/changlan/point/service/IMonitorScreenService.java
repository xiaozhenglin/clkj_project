package com.changlan.point.service;

import java.util.List;

import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;

public interface IMonitorScreenService {
	
	List<PointCountEntity> display(ScreenQuery query);
	
	List<ScreenPointEntity> queryPointId(ScreenQuery query);
	
	public List<Object> searchPoints(ScreenQuery query);
}
