package com.changlan.point.dao;


import java.util.List;

import com.changlan.point.pojo.MonitorScreenQuery;
import com.changlan.point.vo.MonitorScreenVO;

public interface IEquipmentScreenDao {
		
	List<Object> queryPointInfo(String pointId);
	
	List<Object> queryPointCurrentInfo (String search);
}
