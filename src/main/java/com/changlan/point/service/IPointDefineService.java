package com.changlan.point.service;

import java.util.List;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.point.pojo.PointInfoDetail;

public interface IPointDefineService {

	List<PointInfoDetail> getAll(TblPointsEntity entity); 

	Boolean existPointpName(TblPointsEntity entity);  
	
	TblPointsEntity getByRegistPackage(String registPackage);
}
