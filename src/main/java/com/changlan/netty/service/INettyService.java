package com.changlan.netty.service;

import com.changlan.common.pojo.MyDefineException;

public interface INettyService {
	//服务器往客户端发送指令
	void serverSendMessage(String registPackage, String message) throws Exception;
	
	//大屏弹框的推送消息
	void serverSendMessageBox(Object messageBox) throws Exception;
	
	//客户端往服务器发送指令
	void clientSendMessage(String ip, String commandContent) throws Exception;
	
	//保存返回指令
	Integer saveReturnMessage(String registPackage,String message) throws Exception; 

	//解析返回指令
	void analysisData(Integer commandRecordId, String registPackage, String receiveMessage)  throws Exception;

	//定时发送指令任务
	void task();


}
