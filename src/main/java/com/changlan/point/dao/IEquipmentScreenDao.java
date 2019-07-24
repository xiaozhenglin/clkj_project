package com.changlan.point.dao;


import java.util.List;

import com.changlan.point.pojo.CommonDataQuery;



public interface IEquipmentScreenDao {
		
    List<Object> queryPointIndicatorList(CommonDataQuery query) ;
	
    List<Object> queryTemperatureIndicatorList(CommonDataQuery query);

    List<Object> queryPartDischargeIndicatorList(CommonDataQuery query);
}
