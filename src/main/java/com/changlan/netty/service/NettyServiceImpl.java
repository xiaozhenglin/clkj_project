package com.changlan.netty.service;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changlan.alarm.service.IAlarmService;
import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblPoinDataEntity;
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
import com.changlan.netty.server.NettyServer;
import com.changlan.point.pojo.PoinErrorType;

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
			map.put("pointRegistPackage", new ParamMatcher(registPackage));
			map.put("commandRecordId", new ParamMatcher(commandRecordId));
	     	List findByMoreFiled = crudService.findByMoreFiled(TblCommandRecordEntity.class, map, true);
	     	//正常只有一个
	    	for(Object o : findByMoreFiled) {
	    		TblCommandRecordEntity entity = (TblCommandRecordEntity)o;
	    		entity.setBackContent(receiveMessage);
	    		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
	    	}
	    	return commandRecordId;
		} catch (Exception e) {
			logger.info(e.getMessage()); 
		}finally {
			//不管有没有保存成功都要移除限制，否则会死锁
			// 
//			if(!canSendRecord.isEmpty()) {
//				//清除防止死锁
//				canSendRecord.remove(registPackage);
//		    	NettyController.setMap(canSendRecord); 
//			}
		}
		return null;
	}

	//解析返回的数据
	@Override
	public void analysisData(Integer commandRecordId, String registPackage, String receiveMessage) {
    	//找到解析具体的类别-》解析协议-》保存数据
    	List<CommandRecordDetail> recordDetails = recordService.getList(commandRecordId,registPackage,receiveMessage);
    	if(!ListUtil.isEmpty(recordDetails)) {
    		//解析后保存入库的数据
    		logger.info("第四步：获取操作记录返回的内容 commandRecordId：" + recordDetails.get(0).getRecord().getCommandRecordId() 	+"---》》》执行解析数据"+receiveMessage);
    		List<TblPoinDataEntity> pointData = recordService.anylysisData(recordDetails.get(0));
//    		try {
    			//解析是否报警，报警出错不能让保存的指标值回退
    			logger.info("第五步-----》报警规则计算开始");
    			alarmService.anylysisPointData(pointData);
    			logger.info("-----》报警规则计算结束");
//			} catch (Exception e) {
//				logger.info("-----》报警规则计算错误"+e.getMessage());
//			}
    	}
	}

	
}
