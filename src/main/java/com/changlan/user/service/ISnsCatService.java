package com.changlan.user.service;

import java.util.List;

import com.changlan.common.pojo.SmsParams;

public interface ISnsCatService {
	void initSmsCat();
	
	void canSendMsgOrNot();
	
	void sendSmsCat();
	
	void updateRecord();
	
	void analysis();
	
}
