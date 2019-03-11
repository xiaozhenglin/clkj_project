package com.changlan.point.pojo;

public enum PoinErrorType {
	COMPANY_GROUP_NAME_EXIST("P001","公司组名称已经存在"), 
	COMPANY_NAME_EXIST("P002","公司名称重复"),
	POINT_CATEGORY_NAME_EXIST("P004","监控类别名称重复"),
	NAME_EXIST("P005","名称重复"),
	SAVE_EROOR("P003","保存或修改信息失败"),
	CHANNEL_IS_NOT_ACTIVE("N001","发送失败,设备未连接或 监控点注册包无效或者 netty通道不可用"),
	LOCK_IP_SEND_RECORD("N002","当前ip设备暂不能发送指令,等待上次接受返回值");
	
	private String code;
	private String name;
	private PoinErrorType(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
