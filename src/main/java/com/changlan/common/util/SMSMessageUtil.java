package com.changlan.common.util;

import org.springframework.http.ResponseEntity;

import com.changlan.common.pojo.SmsParams;

public class SMSMessageUtil {
	
	public static final String uid = "a1445023633";
	public static final String key = "3b78dbe257924cb38d14"; //用户 后台设置密码
	public static final String smsUrl = "http://utf8.api.smschinese.cn/?";
	public static final String smsNumber = "http://www.smschinese.cn/web_api/SMS/?Action=SMS_Num";
	
	/**
	 * 中国网建发送短信
	 **/
	public static void sendMessage(String smsMob, String smsText) { 
		//电话号码用逗号分隔,可以一次性发送多个
		SmsParams params = new 	SmsParams(uid, key, smsMob, smsText);
		ResponseEntity<Object> postForEntity = RestUtil.postForEntity(smsUrl, params, Object.class);
		Object body = postForEntity.getBody(); 
		System.out.println(body.toString()); 
	}
	
	/**
	 * 中国网建获取短信数量
	 **/
	public static Integer getSmsNumber() { 
		String url = smsNumber+"&Uid="+uid+"&Key="+key;
		ResponseEntity<Object> forEntity = RestUtil.getForEntity(url, Object.class); 
		System.out.println(forEntity.getBody().toString()); 
		return (Integer)forEntity.getBody();
	}

	public static void main(String[] args) throws Exception {
		Integer Number = getSmsNumber();  
		if(Number>0) {
			sendMessage("18390820674","验证码:123456[长缆电工科技]");
		}
		throw new Exception("短信数量不足");
	}
	
}
