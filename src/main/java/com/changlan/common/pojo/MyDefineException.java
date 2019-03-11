package com.changlan.common.pojo;

import com.changlan.point.pojo.PoinErrorType;
import com.changlan.user.pojo.UserErrorType;

public class MyDefineException extends Exception{
	
	private Boolean success;
	private String code;
	private String msg;
	private Object data;
	
	public MyDefineException(String code, String msg, Boolean success, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.success = success;
		this.data = data;
	}
	
	
	public MyDefineException(PoinErrorType type) {
		super();
		this.success = false;
		this.code = type.getCode();
		this.msg = type.getName();
		this.data = null;
	}

	public MyDefineException(UserErrorType result) {
		this.success= false;
		this.code = result.getCode();
		this.msg = result.getMsg();
		this.data = null;
	}

	public MyDefineException() {
		super();
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
