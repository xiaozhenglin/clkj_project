package com.changlan.indicator.pojo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblAlarmRuleEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.service.IIndicatorCategoryService;

public class IndiCatorValueDetail  {
	private TblIndicatorCategoriesEntity category ; //一个类别
	private TblIndicatorValueEntity indicatorValue; 	//一个指标 所属 一个类别
	
	public IndiCatorValueDetail(TblIndicatorValueEntity entity) { 
		this.indicatorValue = entity;
		this.category  = getCategory(entity);
	}
	
	private TblIndicatorCategoriesEntity getCategory(TblIndicatorValueEntity entity) {
		IIndicatorCategoryService categoryService = SpringUtil.getBean(IIndicatorCategoryService.class);
		List<TblIndicatorCategoriesEntity> all = categoryService.getAll(entity.getCategoryId()); 
		if(!ListUtil.isEmpty(all)) {
			return all.get(0); 
		}
		return null;
	}


	public TblIndicatorValueEntity getIndicatorValue() {
		return indicatorValue;
	}
	public void setIndicatorValue(TblIndicatorValueEntity indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	public TblIndicatorCategoriesEntity getCategory() {
		return category;
	}

	public void setCategory(TblIndicatorCategoriesEntity category) {
		this.category = category;
	}
	
	
	
}
