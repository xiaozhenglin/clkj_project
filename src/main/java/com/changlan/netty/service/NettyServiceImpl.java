package com.changlan.netty.service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.changlan.common.entity.TblCommandCategoryEntity;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.entity.TblTemperatureDataEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.AnalysisDataUtil;
import com.changlan.common.util.CRC16M;
import com.changlan.common.util.FastjsonUtil;
import com.changlan.common.util.ListUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.indicator.service.IIndicatoryValueService;
import com.changlan.netty.HttpNettyServer;
import com.changlan.netty.client.ModbusClient;
import com.changlan.netty.controller.ConnectClients;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.pojo.MyTask;
import com.changlan.netty.server.NettyServer;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.user.pojo.LoginUser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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
	public void serverSendMessage(String registPackage, String message) throws Exception { 
		//对发送消息进行crc校验
	   byte[] sbuf = CRC16M.getSendBuf(message.substring(0,message.length()-4));
	   boolean equalsIgnoreCase = message.equalsIgnoreCase(CRC16M.getBufHexStr(sbuf).trim()); 
	   if(!equalsIgnoreCase) {
		   //清除防止死锁
		   NettyController.map.remove(registPackage);
		   throw new MyDefineException(PoinErrorType.SEND_CRC_ERROR); 
	   }
		
		//registPackage 对应的通道
 		Iterator<Entry<Object, Channel>> iterator = NettyServer.channelMap.entrySet().iterator();
 		Boolean sendSuccess = false;
 		while(iterator.hasNext()) {
 			Entry<Object, Channel> next = iterator.next(); 
 			String key = next.getKey().toString();
 			if(key.equals(registPackage)) {
 				Channel channel = next.getValue();
 				//channel为一个接口，如果断线，这个接口获取的状态会随之改变。
 				if(channel!=null && channel.isActive()) {
 					ByteBuf buf = Unpooled.buffer(3000);
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
	
	@Override
	public void serverSendMessageBox(Object messageBox) throws Exception {
		//registPackage 对应的通道
 		Map<Object, Channel> messageChannelMap = HttpNettyServer.messageChannelMap; 
 		Boolean sendSuccess = false;
 		if(!messageChannelMap.isEmpty()) {
 			Channel channel = messageChannelMap.get("messageBox"); 
 			if(channel!=null &&channel.isActive()) {
				ByteBuf buf = Unpooled.buffer(3000);
				String beanToJson = FastjsonUtil.beanToJson(messageBox);
				channel.writeAndFlush(new TextWebSocketFrame(beanToJson));
				sendSuccess =  true ; 
 			}
 		}	
// 		if(!sendSuccess) {
// 			throw new MyDefineException(PoinErrorType.CHANNEL_IS_NOT_ACTIVE); 
// 		}
	}
	
	@Override
	public void clientSendMessage(String ip, String message) throws Exception {
		//客户端往服务器测温度不需要CRC16校验
		Boolean sendSuccess = false;
		
		//获取通道
		Channel connectChannel  ;
		if(ConnectClients.clients.isEmpty() ||   ConnectClients.clients.get(ip)==null) {
			ModbusClient client = new ModbusClient(ip,502);
			client.run();
			ConnectClients.clients.put(ip, client.getConnectChannel());
			connectChannel = client.getConnectChannel();
		}else {
			connectChannel = ConnectClients.clients.get(ip);
		}
		
		//发送指令
		if(connectChannel.isActive()) {
			ByteBuf buf = Unpooled.buffer(3000);
			byte[] bytes =  StringUtil.hexStringToBytes(message);
			connectChannel.writeAndFlush(buf.writeBytes(bytes)); 
			sendSuccess =  true ; 
		}
		
 		if(!sendSuccess) {
 			throw new MyDefineException(PoinErrorType.CHANNEL_IS_NOT_ACTIVE); 
 		}
	}
	

	//客户端-》服务器数据 保存记录
	@Override
	@Transactional
	public Integer saveReturnMessage(String registPackageOrIp, String receiveMessage) {
		Integer saveRecordId ;
		Map<String, Integer> registPackageAndRecord = NettyController.map; 
		try {
	    	//
			if(!registPackageAndRecord.isEmpty() && registPackageAndRecord.get(registPackageOrIp) !=null) {
		    	Map map = new HashMap();
		    	Integer commandRecordId =registPackageAndRecord.get(registPackageOrIp) ; 
		    	saveRecordId = commandRecordId;
		    	map.put("commandRecordId", new ParamMatcher(commandRecordId));
		     	List findByMoreFiled = crudService.findByMoreFiled(TblCommandRecordEntity.class, map, true);
		     	//正常只有一个
		    	for(Object o : findByMoreFiled) {
		    		TblCommandRecordEntity entity = (TblCommandRecordEntity)o;
		    		//判断地址码是否一致
		    		String commandContentAddress = entity.getCommandContent().substring(0,2);
		    		String receiveMessageAddress = receiveMessage.substring(0,2);
		    		if(commandContentAddress.equals(receiveMessageAddress)) {
		    			entity.setBackContent(receiveMessage);
			    		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
		    		}
		    	}
		    	//清除防止死锁
	    		NettyController.map.remove(registPackageOrIp);
	    		return saveRecordId;
			}
		} catch (Exception e) {
			logger.error(this.getClass() + "接受指令保存数据出错==" + e.getMessage()); 
		}finally {
			//不管有没有保存成功都要移除限制，否则会死锁
			if(!registPackageAndRecord.isEmpty() && registPackageAndRecord.get(registPackageOrIp) !=null ) {
				//清除防止死锁
				NettyController.map.remove(registPackageOrIp);
			}
		}
		return null;
	}
	
	//记录id是否重新发送
//	public static Map<Integer,Boolean> map = new HashMap();

	//解析返回的数据
	@Override
	public void analysisData(Integer commandRecordId, String registPackage, String receiveMessage) throws Exception { 
    	//找到具体的解析协议-> 解析->保存数据
    	List<CommandRecordDetail> recordDetails = recordService.getList(commandRecordId,registPackage,receiveMessage);
    	if(!ListUtil.isEmpty(recordDetails)) {
    		//解析后保存入库的数据
    		logger.info("第四步：commandRecordId：" + recordDetails.get(0).getRecord().getCommandRecordId() 	+"---》》》执行解析数据"+receiveMessage);
    		TblCommandCategoryEntity category = recordDetails.get(0).getCategory();
    		//电流、电压、温度解析后的数据
    		List<Object> pointData = recordService.anylysisData(recordDetails.get(0));
    		if(!ListUtil.isEmpty(pointData)) {
    			//解析是否报警
    			logger.info("解析数据完成-----》报警规则计算开始");
    			
    			List<TblPoinDataEntity> currentAndVoltage = new ArrayList();
    			List<TblTemperatureDataEntity> temperature = new ArrayList<>(); 
    			for(Object o: pointData) {
    				if(o instanceof  TblPoinDataEntity) {
    					currentAndVoltage.add((TblPoinDataEntity)o);
    				}
    				if(o instanceof  TblTemperatureDataEntity) {
    					temperature.add((TblTemperatureDataEntity)o);
    				}
    			}
    			
    			if(!ListUtil.isEmpty(currentAndVoltage)) {
    				alarmService.anylysisPointData(currentAndVoltage);
    			}
    			if(!ListUtil.isEmpty(temperature)) {
    				alarmService.anylysisTemperatureData(temperature);
    			}
    			logger.info("-----》》》根据报警规则计算结束");
    			//重试发送指令确认报警
//    			if(haveAlarm ) {
//    				resend(recordDetails.get(0).getCommandDefault(),registPackage);
//    			}
    		}
    	}
	}

//	private void resend(TblPointSendCommandEntity commandDefault, String registPackage) { 
//		if(NettyController.canSendRecord(registPackage)) {
//			//一次发一条。加锁操作
//			recordService.update(commandDefault,registPackage);
//			try {
//				sendMessage(registPackage,commandDefault.getCommandContent());
//			} catch (Exception e) {
//				logger.info(e.getMessage());
//			}
//		}
//	}

	
	@Override
	public void task() {
		logger.info("===>>执行定时发送指令任务"); 
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		List<CommandDefaultDetail> commandList = commandDefaultService.commandList(null); 
		for(CommandDefaultDetail data : commandList) {
			try {
				//每个数据延时0.5秒按顺序开启定时任务
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TblCommandCategoryEntity category = data.getCategory(); 
			//温度检测为 本机client向服务器发送 ， 电流电压为本机server向机器发送指令
			if(category.getCategoryNmae().indexOf("温度")<-1) {
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


}
