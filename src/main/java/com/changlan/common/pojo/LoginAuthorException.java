package com.changlan.common.pojo;

import javax.servlet.ServletException;

public class LoginAuthorException extends RuntimeException{
	private Boolean success;
	private String code;
	private String msg;
	private Object data;
	public LoginAuthorException(Boolean success, String code, String msg, Object data) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public LoginAuthorException() {
		super();
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
