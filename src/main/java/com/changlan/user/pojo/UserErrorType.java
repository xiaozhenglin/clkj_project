package com.changlan.user.pojo;

public enum UserErrorType {
	USER_NOT_LOGIN("A001","用户未登录"),
	LOGIN_ERROR("A002","登录失败"), 
	CANNOT_EDIT_OTHER("A003","只允许超级管理员或者自己修改自己的信息"),
	ONLY_SUPER_SAVE_OTHER("A004","只允许超级管理员添加或者信息"),
	NO_AUTHORITY("A005","请检查地址是否正确,用户没有登录或者用户没有访问权限"),
	USER_NAME_EXIST("A006","用户名已经存在"),
	EDIT_ERROR("A007","修改失败"), 
	SAVE_ERROR("A008","保存失败"),
	PASS_LENGTH("A009","密码长度不能小于8"),
	USER_ROLE_EXIST("A010","用户角色已经存在"),
	NAME_EXIST("A011","名称重复"),
	UPLOAD_ERROR("A012","上传文件错误"), 
	VERIFIED_NULL("A013","验证码为空"),
    VERIFIED_ERROR("A014","验证失败");
	
	private String code;
	private String msg;
	
	private UserErrorType(String code, String msg) {
		this.code = code;
		this.msg = msg;
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
	
	
}
