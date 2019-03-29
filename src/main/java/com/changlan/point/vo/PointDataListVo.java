package com.changlan.point.vo;

import java.util.List;

import org.springframework.data.domain.Page;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.point.pojo.PointDataDetail;

public class PointDataListVo {

	private TblPointsEntity point ; //没有加get set方法
	private Page<PointDataDetail> pointDatas;

//	public PointDataListVo(List<PointDataDetail> pointDatas) {
//		this.pointDatas = pointDatas;
//	}

	public PointDataListVo() {
		super();
	}

	public PointDataListVo(Page<PointDataDetail> pointDatas) {
//		this.point = point2;
		this.pointDatas = pointDatas;
	}

	public Page<PointDataDetail> getPointDatas() {
		return pointDatas;
	}

	public void setPointDatas(Page<PointDataDetail> pointDatas) {
		this.pointDatas = pointDatas;
	}

//	public List<PointDataDetail> getPointDatas() {
//		return pointDatas;
//	}
//
//	public void setPointDatas(List<PointDataDetail> pointDatas) {
//		this.pointDatas = pointDatas;
//	}
	
	
}
