package com.changlan.netty.service;

import com.changlan.common.pojo.MyDefineException;

public interface INettyService {
	//发送指令
	void sendMessage(String registPackage, String message) throws Exception;
	
	//保存返回指令
	Integer saveReturnMessage(String registPackage,String message) throws Exception; 

	//解析返回指令
	void analysisData(Integer commandRecordId, String registPackage, String receiveMessage);  
}
