package com.changlan.common.pojo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.lang.Exception;

public class BaseResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2969936483908577558L;
	private Boolean success;
	private String code;
	private String msg;
	private Object data;
	
	public BaseResult(String code, String msg, Boolean success, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.success = success;
		this.data = data;
	}
	
	public BaseResult() {
		super();
	}

	public BaseResult(MyDefineException e) {
		this.code = e.getCode();
		this.msg = e.getMsg();
		this.success = e.getSuccess();
		this.data = e.getData();
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
