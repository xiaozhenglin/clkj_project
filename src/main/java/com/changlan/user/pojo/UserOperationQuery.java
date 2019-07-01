package com.changlan.user.pojo;

import java.util.Date;

import com.changlan.common.entity.TblUserOperationEntity;

public class UserOperationQuery extends TblUserOperationEntity{
	
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
