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
		String registPackage = event.getRegistPackage(); //设备注册包 或者设备ip
		String receiveMessage = event.getReceiveMessage(); 
		INettyService nettyService = SpringUtil.getBean(INettyService.class);
		try {
			nettyService.analysisData(commandRecordId,registPackage,receiveMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
