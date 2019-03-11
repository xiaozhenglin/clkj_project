package com.changlan.netty.event;

import org.springframework.context.ApplicationEvent;

public class CommandCallBackEvent extends ApplicationEvent{
	
	private String registPackage;
	private String receiveMessage ;
	

	public CommandCallBackEvent(Integer commandRecordId, String registPackage, String receiveMessage) {
		super(commandRecordId);
		this.registPackage = registPackage;
		this.receiveMessage = receiveMessage;
	}

	public String getReceiveMessage() {
		return receiveMessage;
	}

	public void setReceiveMessage(String receiveMessage) {
		this.receiveMessage = receiveMessage;
	}

	public String getRegistPackage() {
		return registPackage;
	}

	public void setRegistPackage(String registPackage) {
		this.registPackage = registPackage;
	}


	

}
