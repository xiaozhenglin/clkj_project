package com.changlan.common.pojo;

public class ParamMatcher {
	
    private MatcheType type;
    private Object value;

	public ParamMatcher() {
		super();
	}

	public ParamMatcher(MatcheType type, Object value) {
		super();
		this.type = type;
		this.value = value;
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
	
	
}
