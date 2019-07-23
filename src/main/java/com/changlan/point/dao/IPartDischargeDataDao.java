package com.changlan.point.dao;

import java.util.Date;
import java.util.List;


import com.changlan.other.entity.DeviceDataColl;

public interface IPartDischargeDataDao {
	
	
	//获取表格数据
	List<DeviceDataColl> getTableData(Date begin, Date end,Integer indicators, Integer pointId);   
}
