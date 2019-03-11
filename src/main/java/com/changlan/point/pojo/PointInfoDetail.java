package com.changlan.point.pojo;

import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.service.IPointCategoryService;

public class PointInfoDetail extends LineDetail{

	private TblPointsEntity point; //一个监控点
	private TblPointCategoryEntity category;  //当前监控点所属类别
	
	public PointInfoDetail() {
		super();
	}
	
	public PointInfoDetail(TblPointsEntity entity,TblLinesEntity line) { 
		super(line);
		this.point = entity;
		List<TblPointCategoryEntity> all = getCategory(entity);
		if(!ListUtil.isEmpty(all)) {
			this.category = (TblPointCategoryEntity)all.get(0);
		}
	}
	//当前监控点所属类别
	private List<TblPointCategoryEntity> getCategory(TblPointsEntity entity) {
		IPointCategoryService categoryService = SpringUtil.getBean(IPointCategoryService.class);
		TblPointCategoryEntity cate = new TblPointCategoryEntity();
		cate.setPointCatgoryId(entity.getPointCatagoryId()); 
		List<TblPointCategoryEntity> all = categoryService.getAll(cate); 
		return all;
	}

	public TblPointsEntity getPoint() {
		return point;
	}

	public void setPoint(TblPointsEntity point) {
		this.point = point;
	}

	public TblPointCategoryEntity getCategory() {
		return category;
	}

	public void setCategory(TblPointCategoryEntity category) {
		this.category = category;
	}

	
}
