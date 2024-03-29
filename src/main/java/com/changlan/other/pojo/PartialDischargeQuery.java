package com.changlan.other.pojo;

import java.util.Date;

import com.changlan.other.entity.PartialDischargeEntity;

public class PartialDischargeQuery {
	private Long begin;
	private Long end;
	private String localtion;
	private Integer pointId;
	private Integer lineId;
    private Integer CategoryId;
    private Integer channelSettings_id;
    private Integer record_id;

        
	public Integer getRecord_id() {
		return record_id;
	}

	public void setRecord_id(Integer record_id) {
		this.record_id = record_id;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	

	public Integer getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(Integer categoryId) {
		CategoryId = categoryId;
	}


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

	public String getLocaltion() {
		return localtion;
	}
	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getChannelSettings_id() {
		return channelSettings_id;
	}

	public void setChannelSettings_id(Integer channelSettings_id) {
		this.channelSettings_id = channelSettings_id;
	}

	
	
}
