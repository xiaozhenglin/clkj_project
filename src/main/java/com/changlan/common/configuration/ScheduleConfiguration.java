package com.changlan.common.configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.changlan.common.pojo.BaseResult;
import com.changlan.common.util.SpringUtil;
import com.changlan.netty.service.INettyService;

@Component
public class ScheduleConfiguration {
	
	 	@Scheduled(cron = "0/10 * * * * *")
	    public void work() {
	 		INettyService nettyService = SpringUtil.getBean(INettyService.class); 
	    	BaseResult baseResult = new BaseResult("", "", true, "testData"); 
	    	try {
				nettyService.serverSendMessageBox(baseResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
}
