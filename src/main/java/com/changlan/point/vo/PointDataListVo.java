package com.changlan.point.vo;

import java.util.List;

import org.springframework.data.domain.Page;

import com.changlan.point.pojo.PointDataDetail;

public class PointDataListVo {

//	private List<PointDataDetail> pointDatas;
	private Page<PointDataDetail> pointDatas;

//	public PointDataListVo(List<PointDataDetail> pointDatas) {
//		this.pointDatas = pointDatas;
//	}

	public PointDataListVo() {
		super();
	}

	public PointDataListVo(Page<PointDataDetail> pointDatas) {
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
