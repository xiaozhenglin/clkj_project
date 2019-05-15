package com.changlan.point.vo;

import javax.persistence.Column;

import com.changlan.common.entity.TblPointCategoryEntity;

public class PointCategoryVO {
	
    private Integer pointCatgoryId; //类别id
    private String  pontCatagoryName; //类别名称
    
	public PointCategoryVO(TblPointCategoryEntity category) {
		this.pointCatgoryId = category.getPointCatgoryId();
		this.pontCatagoryName = category.getPontCatagoryName();
	}
	

	public PointCategoryVO() {
		super();
	}


	public Integer getPointCatgoryId() {
		return pointCatgoryId;
	}


	public void setPointCatgoryId(Integer pointCatgoryId) {
		this.pointCatgoryId = pointCatgoryId;
	}


	public String getPontCatagoryName() {
		return pontCatagoryName;
	}


	public void setPontCatagoryName(String pontCatagoryName) {
		this.pontCatagoryName = pontCatagoryName;
	}

	
	
	
}
