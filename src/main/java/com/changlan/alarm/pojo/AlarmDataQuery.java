package com.changlan.alarm.pojo;

import com.changlan.common.entity.TblPointAlamDataEntity;

public class AlarmDataQuery extends TblPointAlamDataEntity{
	
	private Long begin;
	private Long end;
	private String pointNameOrLineNameOrChannelName;
	
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
	
	public String getPointNameOrLineNameOrChannelName() {
		return pointNameOrLineNameOrChannelName;
	}
	public void setPointNameOrLineNameOrChannelName(String pointNameOrLineNameOrChannelName) {
		this.pointNameOrLineNameOrChannelName = pointNameOrLineNameOrChannelName;
	}
			
}
