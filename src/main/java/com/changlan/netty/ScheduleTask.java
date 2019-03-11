//package com.changlan.netty;
//
//import java.util.Map;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.changlan.netty.controller.NettyController;
//
//@Component
//public class ScheduleTask {
//	
//	
//	@Scheduled(cron ="0/10 * * * * ?")
//    public void reportCurrentTime(){
//		Map<String, Integer> map = NettyController.getMap(); 
//		if(!map.isEmpty()) {
//			//清除防止死锁
//			map.clear();
//			NettyController.setMap(map); 
//		}
//    }
//}
