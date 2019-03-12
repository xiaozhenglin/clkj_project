package com.changlan.netty.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.service.INettyService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.user.pojo.LoginUser;

@Component
public class MyTask extends TimerTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TblPointSendCommandEntity commandDefault;
	
	public MyTask(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}
	
	public MyTask() {
		super();
	}

	@Override
	public void run() {
		System.out.println("运行定时器"); 
		if(NettyController.canSendRecord(commandDefault.getRegist())) {
			//一次发一条。加锁操作
			INettyService nettyService = SpringUtil.getBean(INettyService.class);
			try {
				nettyService.sendMessage(commandDefault.getRegist(),commandDefault.getCommandContent());
			} catch (Exception e) {
				logger.info(e+"");
			} 
			afterSaveToRecord(commandDefault);
		}
	}
	
	private void afterSaveToRecord(TblPointSendCommandEntity commandDefault) {
		ICommandRecordService recordService = SpringUtil.getBean(ICommandRecordService.class);
		recordService.update(commandDefault);
	}

	
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Timer timer = new Timer();
		timer.schedule(task,0,1000);
	}


	public TblPointSendCommandEntity getCommandDefault() {
		return commandDefault;
	}


	public void setCommandDefault(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}
	
}
