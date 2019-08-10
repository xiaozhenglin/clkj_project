package com.changlan.point.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.point.pojo.PointInfoDetail;

public interface IPointDefineService {

	List<PointInfoDetail> getAll(TblPointsEntity entity); 

	Boolean existPointpName(TblPointsEntity entity);  
	
	TblPointsEntity getByRegistPackageOrId(Integer pointId,String registPackage);

	Page<PointInfoDetail> getPage(TblPointsEntity entity, Pageable pageable);

	Boolean initPointStatus();
}
