package com.changlan.point.service;

import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;

public interface ICompanyGropService {

	Boolean existGroupName(TblCompanyGroupEntity entity); 

	List<TblCompanyGroupEntity> getAllGroup(TblCompanyGroupEntity group);


}
