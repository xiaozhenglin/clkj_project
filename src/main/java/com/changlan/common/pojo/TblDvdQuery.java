package com.changlan.common.pojo;

import com.changlan.common.entity.TblDvdEntity;

public class TblDvdQuery extends TblDvdEntity{
	
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
