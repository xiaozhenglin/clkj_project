package com.changlan.point.service;

import java.util.List;

import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.vo.MonitorScreenVO;

public interface IMonitorScreenService {
	
	List<Object> display(MonitorScreenQuery query);
}