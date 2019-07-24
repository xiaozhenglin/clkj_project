package com.changlan.point.service;


import java.util.List;

import com.changlan.point.pojo.CommonDataQuery;



public interface IEquipmentScreenService {
		
	    List<Object> queryPointIndicatorList(CommonDataQuery query) ;
		
	    List<Object> queryTemperatureIndicatorList(CommonDataQuery query);

	    List<Object> queryPartDischargeIndicatorList(CommonDataQuery query);
}
