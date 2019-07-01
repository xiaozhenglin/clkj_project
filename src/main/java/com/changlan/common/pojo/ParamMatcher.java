package com.changlan.common.pojo;

import java.util.Date;

public class ParamMatcher {
	
    private MatcheType type;
    private Object value;
    private Date begin; //between 类型时候的开始时间
    private Date end; //between时候的结束时间

	public ParamMatcher() {
		super();
	}

	public ParamMatcher(Date begin, Date end) {
		super();
		this.type = MatcheType.BETWEEN;
		this.begin = begin;
		this.end = end;
	}
	
	public ParamMatcher(Long begin, Long end) {
		super();
		this.type = MatcheType.BETWEEN;
		this.begin = new Date(begin);
		this.end = new Date(end);
	}

	public ParamMatcher(MatcheType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	
	public ParamMatcher(MatcheType type, Object value, Date begin, Date end) {
		super();
		this.type = type;
		this.value = value;
		this.begin = begin;
		this.end = end;
	}

	public ParamMatcher(Object value) {
		this.type = MatcheType.EQUALS;
		this.value = value;
	}

	public MatcheType getType() {
		return type;
	}

	public void setType(MatcheType type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	
}
