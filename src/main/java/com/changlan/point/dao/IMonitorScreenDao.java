package com.changlan.point.dao;


import java.util.List;

import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.pojo.ScreenQuery;
import com.changlan.point.vo.MonitorScreenVO;

public interface IMonitorScreenDao {
	List<Object> query(MonitorScreenQuery query);
	
	List<Object> queryPointId(ScreenQuery query);

	List<Object> searchPoints(ScreenQuery query);
}
