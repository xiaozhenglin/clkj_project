package com.changlan.netty.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.changlan.common.util.SpringUtil;
import com.changlan.netty.service.INettyService;

@Component
public class CommandCallBackListener implements ApplicationListener<CommandCallBackEvent>{

	@Override
	public void onApplicationEvent(CommandCallBackEvent event) {
		Integer commandRecordId = (Integer)event.getSource();
		String registPackage = event.getRegistPackage(); 
		String receiveMessage = event.getReceiveMessage(); 
		INettyService nettyService = SpringUtil.getBean(INettyService.class);
		nettyService.analysisData(commandRecordId,registPackage,receiveMessage);
	}

}
