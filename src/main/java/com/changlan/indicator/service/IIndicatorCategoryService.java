package com.changlan.indicator.service;

import java.util.List;

import com.changlan.common.entity.TblIndicatorCategoriesEntity;

public interface IIndicatorCategoryService {

	Boolean existCatergoryName(TblIndicatorCategoriesEntity entity);

	List<Object> getAll(Integer id);    

}
