package com.changlan.point.pojo;

public enum PoinErrorType {
	COMPANY_GROUP_NAME_EXIST("P001","公司组名称已经存在"), 
	COMPANY_NAME_EXIST("P002","公司名称重复"), COMPANY_NOT_EXIST("P006","公司不存在"),
	LINE_NOT_EXITS("P007","线路不存在"),
	POINT_CATEGORY_NAME_EXIST("P004","监控类别名称重复"),
	NAME_EXIST("P005","名称重复"),
	SAVE_EROOR("P003","保存或修改信息失败"),
	CHANNEL_IS_NOT_ACTIVE("N001","发送失败,设备未连接或 监控点注册包无效或者 netty通道不可用"),
	LOCK_POINT_SEND_RECORD("N002","当前设备暂不能发送指令,等待上次接受返回值"),
	SEND_CRC_ERROR("N003","发送的指令不符合CRC校验"),
	RECEIVE_CRC_ERROR("N004","接受的指令不符合CRC校验"), 
	POINT_NOT_EXIST("P008","监控点不存在"),
	POINT_CATEGORY_NOT_EXIST("P009","监控点类别不存在"),
	POINT_REGISTPACKAGE_IS_NULL("P010","监控点注册包为空");
	
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
