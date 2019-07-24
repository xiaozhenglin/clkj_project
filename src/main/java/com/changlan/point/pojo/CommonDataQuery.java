package com.changlan.point.pojo;

import java.io.Serializable;

import com.changlan.common.entity.TblPoinDataEntity;

public class CommonDataQuery {
	
	private Long begin;
	private Long end;
	private Integer categroryId;//指标类别
	private String indicatorIds;
	private Integer pointId;
	private String picture_url;
	
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
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
		
}
