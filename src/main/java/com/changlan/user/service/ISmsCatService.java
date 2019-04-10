package com.changlan.user.service;

import java.util.List;

import com.changlan.common.pojo.SmsParams;

public interface ISmsCatService {
	
	void initSmsCat();
	
	boolean canSendMsgOrNot(Integer pointId);
	
	void sendSmsCat(SmsParams param) throws Exception; 
	
	Object analysis(String receiveMsg,String fromPhone);


	
}
