package com.changlan.alarm.pojo;

import com.changlan.common.entity.TblAlarmDownRecordEntity;

public class AlarmDownRecordQuery extends TblAlarmDownRecordEntity{
	
	public Long begin;
	public Long end;
	
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

}
