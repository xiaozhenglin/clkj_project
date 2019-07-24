package com.changlan.point.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.point.dao.IEquipmentScreenDao;

import com.changlan.point.pojo.CommonDataQuery;
import com.changlan.point.service.IEquipmentScreenService;



@Service
public class EquipmentScreenServiceImpl implements IEquipmentScreenService {
	
	@Autowired
	private IEquipmentScreenDao dao;

	@Override
	public List<Object> queryPointIndicatorList(CommonDataQuery query) {
		// TODO Auto-generated method stub
		List<Object> list = dao.queryPointIndicatorList(query);
		return list;
	}

	@Override
	public List<Object> queryTemperatureIndicatorList(CommonDataQuery query) {
		// TODO Auto-generated method stub
		List<Object> list = dao.queryTemperatureIndicatorList(query);
		return list;
	}

	@Override
	public List<Object> queryPartDischargeIndicatorList(CommonDataQuery query) {
		// TODO Auto-generated method stub
		List<Object> list = dao.queryPartDischargeIndicatorList(query);
		return list;
	} 


	
	
}
