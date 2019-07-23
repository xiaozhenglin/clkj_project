package com.changlan.point.pojo;

import java.io.Serializable;

import com.changlan.common.entity.TblPoinDataEntity;

public class PartDischargeDataQuery {
	
	private Long begin;
	private Long end;
	private Integer categroryId;//指标类别
	private String indicatorIds;
	private Integer pointId;
	
	public Long getBegin() {
		return begin;
	}
	public void setBegin(Long begin) {
		this.begin = begin;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	public Integer getCategroryId() {
		return categroryId;
	}
	public void setCategroryId(Integer categroryId) {
		this.categroryId = categroryId;
	}
	public String getIndicatorIds() {
		return indicatorIds;
	}
	public void setIndicatorIds(String indicatorIds) {
		this.indicatorIds = indicatorIds;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	
}
