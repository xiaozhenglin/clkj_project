package com.changlan.point.vo;

import com.changlan.common.entity.TblPointCategoryEntity;

public class PointCategoryVO {
	
	private TblPointCategoryEntity category;

	public PointCategoryVO(TblPointCategoryEntity category) {
		this.category = category;
	}

	public PointCategoryVO() {
		super();
	}


	public TblPointCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(TblPointCategoryEntity category) {
		this.category = category;
	}
	
	
}
