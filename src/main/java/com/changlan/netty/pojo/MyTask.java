package com.changlan.netty.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.service.ICommandDefaultService;
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

	private TblPointSendCommandEntity commandDefault;
	
	public MyTask(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}
	
	public MyTask() {
		super();
	}

	@Override
	public void run() {
		if(NettyController.canSendRecord(commandDefault.getRegist())) {
			//一次发一条。加锁操作
			INettyService nettyService = SpringUtil.getBean(INettyService.class);
			try {
				nettyService.sendMessage(commandDefault.getRegist(),commandDefault.getCommandContent());
				afterSaveToRecord(commandDefault);
			} catch (Exception e) {
				System.out.println(e); 
			} 
			System.out.println("======>>"+System.currentTimeMillis()); 
		}
	}
	
	private void afterSaveToRecord(TblPointSendCommandEntity commandDefault) {
		ICrudService crudService = SpringUtil.getICrudService(); 
		//保存用户操作指令
		TblCommandRecordEntity entity = new TblCommandRecordEntity();
		entity.setPointRegistPackage(commandDefault.getRegist());
		entity.setAdminUserId(LoginUser.getCurrentUser().getAdminUserId());
		entity.setSendCommandId(commandDefault.getSendCommandId()); 
		entity.setCommandContent(commandDefault.getCommandContent());
		entity.setRecordTime(new Date()); 
		//将记录id保存到会话，当有返回消息时保存起来
		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
		if(update != null) {
			Map<String, Integer> map = NettyController.getMap(); 
			map.put(commandDefault.getRegist(), update.getCommandRecordId());
			NettyController.setMap(map); 
		}
	}

	public static void main(String[] args) {
		MyTask task =  new MyTask();
		task.run();
	}


	public TblPointSendCommandEntity getCommandDefault() {
		return commandDefault;
	}


	public void setCommandDefault(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}
	
}
