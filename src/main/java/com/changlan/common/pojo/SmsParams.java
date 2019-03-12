package com.changlan.common.pojo;

public class SmsParams {
	private String Uid;
	private String Key;
	private String smsMob;
	private String smsText;
	
	
	public SmsParams(String uid, String key, String smsMob, String smsText) {
		super();
		this.Uid = uid;
		this.Key = key;
		this.smsMob = smsMob;
		this.smsText = smsText;
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
	
}
