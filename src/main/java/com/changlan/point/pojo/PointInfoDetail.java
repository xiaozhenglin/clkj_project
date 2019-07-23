package com.changlan.point.pojo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblIndicatorCategoriesEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.point.service.IPointCategoryService;

public class PointInfoDetail {

	private TblPointsEntity point; //一个监控点
	private TblPointCategoryEntity category;  //当前监控点所属类别
	private TblLinesEntity line; //所属线路
	private List<TblIndicatorCategoriesEntity> indicatorCategory;
	
	public PointInfoDetail() {
		super();
	}
	
	public PointInfoDetail(TblPointsEntity entity,TblLinesEntity line) { 
//		super(line);
		this.point = entity;
		List<TblPointCategoryEntity> all = getCategory(entity);
		if(!ListUtil.isEmpty(all)) {
			this.category = (TblPointCategoryEntity)all.get(0);
		}
		this.line = line;
		this.indicatorCategory = getIndicatoryCategory(entity.getIndicators());
		
	}
	
	private List<TblIndicatorCategoriesEntity> getIndicatoryCategory(String indicators) {
		List<TblIndicatorCategoriesEntity> list = new ArrayList<TblIndicatorCategoriesEntity>();
		if(StringUtil.isNotEmpty(indicators)) {
			ICrudService crudService = SpringUtil.getICrudService();
			List<String> stringToList = StringUtil.stringToList(indicators); 
			for(String indicator : stringToList) {
				list.add((TblIndicatorCategoriesEntity)crudService.get(Integer.parseInt(indicator), TblIndicatorCategoriesEntity.class, true)); 
			}
		}
		return list;
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

	public TblLinesEntity getLine() {
		return line;
	}

	public void setLine(TblLinesEntity line) {
		this.line = line;
	}

	public List<TblIndicatorCategoriesEntity> getIndicatorCategory() {
		return indicatorCategory;
	}

	public void setIndicatorCategory(List<TblIndicatorCategoriesEntity> indicatorCategory) {
		this.indicatorCategory = indicatorCategory;
	}

	

	
}
