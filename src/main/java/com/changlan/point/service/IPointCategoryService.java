package com.changlan.point.service;

import java.util.List;

import com.changlan.common.entity.TblPointCategoryEntity;

public interface IPointCategoryService {

	Boolean existName(TblPointCategoryEntity entity);

	List<TblPointCategoryEntity> getAll(TblPointCategoryEntity entity);   

}
