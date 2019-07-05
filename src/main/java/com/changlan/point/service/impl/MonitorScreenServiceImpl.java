package com.changlan.point.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.point.dao.IMonitorScreenDao;
import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.service.IMonitorScreenService;
import com.changlan.point.vo.MonitorScreenVO;
@Service
public class MonitorScreenServiceImpl implements IMonitorScreenService{

	@Autowired
	private IMonitorScreenDao dao; 
	
	@Override
	public List<Object> display(MonitorScreenQuery query) {
		List<Object> list = dao.query(query); 
		return list;
	}

	@Override
	public List<Object> queryPointId(String pointName) {
		List<Object> list = dao.queryPointId(pointName); 
		return list;
	}
	
	

}
