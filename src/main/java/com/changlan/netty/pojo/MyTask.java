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
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.service.INettyService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IPointDefineService;
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
		try {
			logger.info("运行定时器"); 
			Integer pointId = commandDefault.getPointId(); 
			IPointDefineService service  =  SpringUtil.getBean(IPointDefineService.class);
			TblPointsEntity pointDefine = service.getByRegistPackageOrId(pointId, null); 
			if(pointDefine==null ) {
				throw new MyDefineException(PoinErrorType.POINT_NOT_EXIST);
			}
			if( StringUtil.isEmpty(pointDefine.getPointRegistPackage()) ) {
				throw new MyDefineException(PoinErrorType.POINT_REGISTPACKAGE_IS_NULL);
			}
			//是否能发送
			if(NettyController.canSendRecord(pointDefine.getPointRegistPackage())) {
				//一次发一条。加锁操作
				afterSaveToRecord(commandDefault,pointDefine.getPointRegistPackage());
				INettyService nettyService = SpringUtil.getBean(INettyService.class);
				nettyService.serverSendMessage(pointDefine.getPointRegistPackage(),commandDefault.getCommandContent());
			}else {
				throw new MyDefineException(PoinErrorType.LOCK_POINT_SEND_RECORD);
			}
		} catch (Exception e) {
			if(e instanceof MyDefineException) {
				MyDefineException myException = (MyDefineException)e;
				logger.info("定时器发送指令出错"+myException.getMsg()+":"+e.getMessage());
				e.printStackTrace();
			}else {
				logger.info("定时器发送指令出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	private void afterSaveToRecord(TblPointSendCommandEntity commandDefault,String pointRegistPackage) {
		ICommandRecordService recordService = SpringUtil.getBean(ICommandRecordService.class);
		recordService.updateServerRecord(commandDefault,pointRegistPackage);
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
