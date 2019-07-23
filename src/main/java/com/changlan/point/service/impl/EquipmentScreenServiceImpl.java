//package com.changlan.point.service.impl;
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.changlan.point.dao.IEquipmentScreenDao;
//import com.changlan.point.dao.IMonitorScreenDao;
//import com.changlan.point.service.IEquipmentScreenService;
//
//
//
//@Service
//public class EquipmentScreenServiceImpl implements IEquipmentScreenService {
//	
//	@Autowired
//	private IEquipmentScreenDao dao; 
//	
//	@Override
//	public List<Object> queryPointInfo(String pointId){
//		List<Object> list = dao.queryPointInfo(pointId); 
//		return list;
//	}
//
//	@Override
//	public List<Object> queryPointCurrentInfo(String pointId) {
//		List<Object> list = dao.queryPointCurrentInfo(pointId); 
//		return list;
//	};
//	
//	
//}
