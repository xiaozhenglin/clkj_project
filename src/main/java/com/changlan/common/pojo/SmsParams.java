package com.changlan.common.pojo;

public class SmsParams {
	private String Uid;  //网建用户id
	private String Key;		//网建用户秘钥
	private String smsMob;  //电话号码用逗号分隔,可以一次性发送多个
	private String smsText; //发送文字
	private String portName; //串口名称
	private int portBaud; //波动率
	private Integer pointId; 
	
	public SmsParams(String uid, String key, String smsMob, String smsText) {
		super();
		this.Uid = uid;
		this.Key = key;
		this.smsMob = smsMob;
		this.smsText = smsText;
	}
	
	
	public SmsParams(String portName, int portBaud) {
		super();
		this.portName = portName;
		this.portBaud = portBaud;
	}



	public SmsParams() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getUid() {
		return Uid;
	}
	public void setUid(String uid) {
		Uid = uid;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	
	public String getSmsMob() {
		return smsMob;
	}


	public void setSmsMob(String smsMob) {
		this.smsMob = smsMob;
	}


	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}


	public String getPortName() {
		return portName;
	}


	public void setPortName(String portName) {
		this.portName = portName;
	}


	public int getPortBaud() {
		return portBaud;
	}


	public void setPortBaud(int portBaud) {
		this.portBaud = portBaud;
	}


	public Integer getPointId() {
		return pointId;
	}


	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	
}
