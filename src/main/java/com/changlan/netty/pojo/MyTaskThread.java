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
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.server.NettyServer;
import com.changlan.netty.service.INettyService;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.pojo.LoginUser;


public class MyTaskThread extends Thread {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TblPointSendCommandEntity commandDefault;
	
	
	
	public MyTaskThread(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}
	
	public MyTaskThread() {
		super();
	}

	@Override
	public void run() {
		try {
			TblPointSendCommandEntity command = new TblPointSendCommandEntity();
			command.setIs_controller("0");
			command.setSystem_start("yes");
			while(true) {
				ICommandDefaultService commandDefaultService  =  SpringUtil.getBean(ICommandDefaultService.class);
				List<CommandDefaultDetail> commandList = commandDefaultService.commandList(command); 			
				for(CommandDefaultDetail data : commandList) {
					try {
						//每个数据延时3秒按顺序开启定时任务 
						Thread.sleep(9000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TblCommandCategoryEntity category = data.getCategory(); 
					//温度检测为 本机client向服务器发送 ， 电流电压为本机server向机器发送指令
	
						TblPointSendCommandEntity commandDefault = data.getCommandDefault(); 
						Integer intervalTime = commandDefault.getIntervalTime();
						if(commandDefault.getIntervalTime()!=null) {
							intervalTime = commandDefault.getIntervalTime();
						}
						
						ICrudService crudService = SpringUtil.getICrudService();
						TblPointsEntity point = (TblPointsEntity) crudService.get(commandDefault.getPointId(), TblPointsEntity.class, true);
						if(point.getStatus().equals("CONNECT")) {
						//logger.info(commandDefault.getCommandContent() + "休眠时间（秒）" + intervalTime);
							if(intervalTime!=null) {							
							    //sleep(intervalTime*1000);
								sendCommandDefault(commandDefault);							
							}else {
								//sleep(120*1000);
								sendCommandDefault(commandDefault);
							}
						}
				}
			}
			
			
		} catch (Exception e) {
			if(e instanceof MyDefineException) {
				MyDefineException myException = (MyDefineException)e;
				logger.info("轮询发送指令出错"+myException.getMsg()+":"+e.getMessage());
				e.printStackTrace();
			}else {
				logger.info("轮询发送指令出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	private void sendCommandDefault(TblPointSendCommandEntity commandDefault) {
		logger.info("运行轮询发送指令》》》》"+commandDefault.getCommandContent()); 
		try {
			Integer pointId = commandDefault.getPointId(); 
			IPointDefineService service  =  SpringUtil.getBean(IPointDefineService.class);
			TblPointsEntity pointDefine = service.getByRegistPackageOrId(pointId, null); 
			if(pointDefine==null ) {
				throw new MyDefineException(PoinErrorType.POINT_NOT_EXIST);
			}
			if( StringUtil.isEmpty(pointDefine.getPointRegistPackage()) ) {
				throw new MyDefineException(PoinErrorType.POINT_REGISTPACKAGE_IS_NULL);
			}
			//判断 设备是否已经 连接并 发送注册包
			if(NettyServer.channelMap.isEmpty()) {
				return;
	//			throw new MyDefineException(PoinErrorType.POINT_NOT_REGIST);
			}
			if(NettyServer.channelMap.get(pointDefine.getPointRegistPackage())==null) {
	//			throw new MyDefineException(PoinErrorType.POINT_NOT_REGIST);
				return;
			}
			Integer intervalTime = commandDefault.getIntervalTime();
			if(commandDefault.getIntervalTime()!=null) {
				intervalTime = commandDefault.getIntervalTime();
			}
			logger.info(commandDefault.getCommandContent() + "休眠时间（秒）" + intervalTime);
			sleep(intervalTime*1000);
			//是否能发送
			if(NettyController.canSendRecord(pointDefine.getPointRegistPackage())) {
				//一次发一条。加锁操作
				afterSaveToRecord(commandDefault,pointDefine.getPointRegistPackage());
				INettyService nettyService = SpringUtil.getBean(INettyService.class);
				nettyService.serverSendMessage(pointDefine.getPointRegistPackage(),commandDefault.getCommandContent());
			}else {
				throw new MyDefineException(PoinErrorType.LOCK_POINT_SEND_RECORD);
			}
			
		}catch (Exception e) {
			if(e instanceof MyDefineException) {
				MyDefineException myException = (MyDefineException)e;
				logger.info("轮询发送指令出错"+myException.getMsg()+":"+e.getMessage());
				e.printStackTrace();
			}else {
				logger.info("轮询发送指令出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void afterSaveToRecord(TblPointSendCommandEntity commandDefault,String pointRegistPackage) {
		ICommandRecordService recordService = SpringUtil.getBean(ICommandRecordService.class);
		recordService.updateServerRecord(commandDefault,pointRegistPackage);
	}

	
	public static void main(String[] args) {
		MyTaskThread task = new MyTaskThread();
		task.run();
	}


	public TblPointSendCommandEntity getCommandDefault() {
		return commandDefault;
	}


	public void setCommandDefault(TblPointSendCommandEntity commandDefault) {
		this.commandDefault = commandDefault;
	}

				
}
