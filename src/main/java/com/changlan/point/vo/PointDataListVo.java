package com.changlan.point.vo;

import java.util.List;

import org.springframework.data.domain.Page;

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.point.pojo.PointDataDetail;

public class PointDataListVo {

	private Integer pointId;
	private String name;
	private Page<PointDataDetail> pointDatas;

	public PointDataListVo() {
		super();
	}

	public PointDataListVo(Integer pointId,Page<PointDataDetail> pointDatas) {
		this.pointId = pointId;
		this.pointDatas = pointDatas;
	}

	public PointDataListVo(TblPointsEntity point, Page<PointDataDetail> pointDatas) {
		this.pointId = point.getPointId();
		this.name = point.getPointName();
		this.pointDatas = pointDatas;
	}

	public void setPointDatas(Page<PointDataDetail> pointDatas) {
		this.pointDatas = pointDatas;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Page<PointDataDetail> getPointDatas() {
		return pointDatas;
	}
	
	
	
}
