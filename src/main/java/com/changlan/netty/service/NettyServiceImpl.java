package com.changlan.netty.service;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.alarm.service.IAlarmService;
import com.changlan.command.pojo.CommandDefaultDetail;
import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.command.service.ICommandDefaultService;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.AnalysisDataUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.pojo.MyTask;
import com.changlan.netty.server.NettyServer;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.user.pojo.LoginUser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

@Service
public class NettyServiceImpl implements INettyService{
	@Autowired
	ICrudService crudService;
	
	@Autowired
	ICommandRecordService recordService;
	
	@Autowired
	IAlarmService alarmService;
	
	@Autowired
	ICommandDefaultService commandDefaultService;
	
	
	protected static final Logger logger = LoggerFactory.getLogger(NettyServiceImpl.class);

	//服务器-》客户端发送数据
	@Override
	public void sendMessage(String registPackage, String message) throws Exception { 
		//registPackage 对应的通道
 		Iterator<Entry<Object, Channel>> iterator = NettyServer.channelMap.entrySet().iterator();
 		Boolean sendSuccess = false;
 		while(iterator.hasNext()) {
 			Entry<Object, Channel> next = iterator.next(); 
 			String key = next.getKey().toString();
 			if(key.equals(registPackage)) {
 				Channel channel = next.getValue();
 				//channel为一个接口，如果断线，这个接口获取的状态会随之改变。
 				if(channel.isActive()) {
 					ByteBuf buf = Unpooled.buffer(300);
 					byte[] bytes =  StringUtil.hexStringToBytes(message);
 					channel.writeAndFlush(buf.writeBytes(bytes)); 
 					sendSuccess =  true ; 
 				}
 			}
 		}
 		if(!sendSuccess) {
 			throw new MyDefineException(PoinErrorType.CHANNEL_IS_NOT_ACTIVE); 
 		}
	}

	//客户端-》服务器数据 保存记录
	@Override
	@Transactional
	public Integer saveReturnMessage(String registPackage, String receiveMessage) {
		Map<String, Integer> canSendRecord = NettyController.map; 
		try {
	    	Map map = new HashMap();
	    	//
	    	Integer commandRecordId = canSendRecord.get(registPackage);  
			map.put("commandRecordId", new ParamMatcher(commandRecordId));
	     	List findByMoreFiled = crudService.findByMoreFiled(TblCommandRecordEntity.class, map, true);
	     	//正常只有一个
	    	for(Object o : findByMoreFiled) {
	    		TblCommandRecordEntity entity = (TblCommandRecordEntity)o;
	    		entity.setBackContent(receiveMessage);
	    		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
	    	}
	    	if(!canSendRecord.isEmpty() && canSendRecord.get(registPackage) !=null ) {
				//清除防止死锁
				canSendRecord.remove(registPackage);
		    	NettyController.setMap(canSendRecord); 
			}
	    	return commandRecordId;
		} catch (Exception e) {
			logger.error(this.getClass() + "==" + e.getMessage()); 
		}finally {
			//不管有没有保存成功都要移除限制，否则会死锁
			if(!canSendRecord.isEmpty() && canSendRecord.get(registPackage) !=null ) {
				//清除防止死锁
				canSendRecord.remove(registPackage);
		    	NettyController.setMap(canSendRecord); 
			}
		}
		return null;
	}
	
	//记录id是否重新发送
	public static Map<Integer,Boolean> map = new HashMap();

	//解析返回的数据
	@Override
	public void analysisData(Integer commandRecordId, String registPackage, String receiveMessage) throws Exception { 
    	//找到解析具体的类别-》解析协议-》保存数据
    	List<CommandRecordDetail> recordDetails = recordService.getList(commandRecordId,registPackage,receiveMessage);
    	if(!ListUtil.isEmpty(recordDetails)) {
    		TblCommandRecordEntity record = recordDetails.get(0).getRecord();
    		//解析后保存入库的数据
    		logger.info("第四步：commandRecordId：" + record.getCommandRecordId() 	+"---》》》执行解析数据"+receiveMessage);
    		List<TblPoinDataEntity> pointData = recordService.anylysisData(recordDetails.get(0));
    		if(!ListUtil.isEmpty(pointData)) {
    			//解析是否报警
    			logger.info("解析数据完成-----》报警规则计算开始");
    			Boolean haveAlarm = alarmService.anylysisPointData(pointData);
    			logger.info("-----》》》根据报警规则计算结束");
    			//重试发送指令确认报警
//    			resend(record,registPackage,haveAlarm);
    		}
    	}
	}

//	private void resend(TblCommandRecordEntity record, String registPackage, Boolean haveAlarm) { 
//		if(haveAlarm && (map== null || map.get(record.getCommandRecordId()) == null)) {
//			try {
//				saveRecord(record,registPackage);
//				sendMessage(registPackage,record.getCommandContent());
//			} catch (Exception e) {
//				logger.info(e.getMessage());
//			}
//			map.put(record.getCommandRecordId(), true);
//		}
//		logger.info("-----》报警规则计算结束");
//	}

	
	@Override
	public void task() {
		logger.info("===>>执行定时发送指令任务"); 
		List<CommandDefaultDetail> commandList = commandDefaultService.commandList(null); 
		for(CommandDefaultDetail data : commandList) {
			try {
				//每个数据延时0.3秒按顺序开启定时任务
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TblPointSendCommandEntity commandDefault = data.getCommandDefault(); 
			Integer intervalTime = commandDefault.getIntervalTime();
			if(intervalTime!=null) {
				MyTask task = new MyTask(commandDefault);
				Timer timer = new Timer();
				//循环执行定时器
				timer.schedule(task, 0, intervalTime*1000);
			}
		}
	}
	
}
