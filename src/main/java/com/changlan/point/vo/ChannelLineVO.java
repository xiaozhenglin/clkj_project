package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class ChannelLineVO {
	
	private Integer lineId;
    private String  title; //名称 
	private List<TblPointCategoryEntity> categorys = new ArrayList<TblPointCategoryEntity>(); //四级包含多个系统类别

	public ChannelLineVO(LineDetail line) {
		TblLinesEntity entity = line.getLine(); 
		this.lineId = entity.getLineId();
		this.title = entity.getLineName(); 
		List<TblPointCategoryEntity> pointCategorys = line.getPointCategorys(); 
		this.categorys = pointCategorys;
	}
	
	public ChannelLineVO() {
		super();
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TblPointCategoryEntity> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<TblPointCategoryEntity> categorys) {
		this.categorys = categorys;
	}


	
}
