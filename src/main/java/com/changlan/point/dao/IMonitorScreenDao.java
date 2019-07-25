package com.changlan.point.dao;


import java.util.List;

import com.changlan.point.entity.LineMonitorCountEntity;
import com.changlan.point.entity.PointCountEntity;
import com.changlan.point.entity.ScreenPointEntity;
import com.changlan.point.pojo.PointQuery;
import com.changlan.point.pojo.ScreenQuery;

public interface IMonitorScreenDao {
	List<PointCountEntity> query(ScreenQuery  query);
	
	List<ScreenPointEntity> queryPoint(ScreenQuery query);
	
	List<LineMonitorCountEntity> queryLine(ScreenQuery query);

}
