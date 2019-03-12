package com.changlan.common.util;

import org.springframework.http.ResponseEntity;

import com.changlan.common.pojo.SmsParams;

public class SMSMessageUtil {
	
	public static final String smsUrl = "http://utf8.api.smschinese.cn/?";
	public static final String key = "3b78dbe257924cb38d14";
	
	public static void sendMessage(String smsMob, String smsText) { 
		//电话号码用逗号分隔,可以一次性发送多个
		SmsParams params = new 	SmsParams("a1445023633", key, smsMob, smsText);
		ResponseEntity<Object> postForEntity = RestUtil.postForEntity(smsUrl, params, Object.class);
		Object body = postForEntity.getBody(); 
		System.out.println(body.toString()); 
	}

	public static void main(String[] args) {
		sendMessage("18390820674","验证码:123456[长缆电工科技]");
	}
	
}
