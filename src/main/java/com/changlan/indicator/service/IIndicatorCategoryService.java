package com.changlan.indicator.service;

import java.util.List;

import com.changlan.common.entity.TblIndicatorCategoriesEntity;

public interface IIndicatorCategoryService {

	Boolean existCatergoryName(TblIndicatorCategoriesEntity entity);

	List<TblIndicatorCategoriesEntity> getAll(Integer id);     

}
