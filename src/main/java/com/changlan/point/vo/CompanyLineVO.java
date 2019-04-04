package com.changlan.point.vo;

import java.util.ArrayList;
import java.util.List;

import com.changlan.common.entity.TblCompanyEntity;
import com.changlan.common.entity.TblLinesEntity;
import com.changlan.common.entity.TblPointCategoryEntity;
import com.changlan.common.util.SpringUtil;
import com.changlan.point.pojo.LineDetail;
import com.changlan.point.service.ILineService;

public class CompanyLineVO {
	
	private Integer lineId;
    private String  title; //名称 
//	private TblLinesEntity line;//第一级一个公司
	
	private List<PointCategoryVO> categorys = new ArrayList<PointCategoryVO>(); //二级包含多条线路信息

	public CompanyLineVO(LineDetail line) {
//		this.line = line.getLine();
		TblLinesEntity entity = line.getLine(); 
		this.lineId = entity.getLineId();
		this.title = entity.getLineName(); 
		List<TblPointCategoryEntity> pointCategorys = line.getPointCategorys(); 
		for(TblPointCategoryEntity category : pointCategorys ) {
			PointCategoryVO vo =new PointCategoryVO(category);
			categorys.add(vo);
		}
	}
	
	public CompanyLineVO() {
		super();
	}


//	public TblLinesEntity getLine() {
//		return line;
//	}
//
//	public void setLine(TblLinesEntity line) {
//		this.line = line;
//	}

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

	public List<PointCategoryVO> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<PointCategoryVO> categorys) {
		this.categorys = categorys;
	}

	

	
	
}
