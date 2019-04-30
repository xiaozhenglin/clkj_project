package com.changlan.netty.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandRecordEntity;
import com.changlan.common.entity.TblCompanyGroupEntity;
import com.changlan.common.entity.TblPointSendCommandEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.GsmCat;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.server.NettyServer;
import com.changlan.netty.server.ServerHandler;
import com.changlan.netty.service.INettyService;
import com.changlan.point.constrant.PointConstrant;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.pojo.LoginUser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

@RestController
@RequestMapping("/admin/netty")
public class NettyController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	INettyService nettyService;
	
	@Autowired
	IPointDefineService pointDefineService;
	
	@Autowired
	ICommandRecordService recordService;
	
	public static Map<String,Integer> map = new HashMap<String,Integer>();
	
    private static final Logger logger = LoggerFactory.getLogger(NettyController.class);
    
    //未加入权限表 关闭短信猫的服务
    @RequestMapping("/close/sms/cat")
  	public ResponseEntity<Object>  closeCat() throws Exception { 
    	GsmCat instance = GsmCat.getInstance(); 
    	logger.info("短信猫服务器的状态:"+instance.getServiceStatus());
    	if(!instance.getServiceStatus().equalsIgnoreCase("STOPPING") ) {
    		instance.stopService();
    	}
      	return success(true);
  	} 
    
    //未加入权限表 测试发送指令用
    @RequestMapping("/test/message")
	public ResponseEntity<Object>  sendMessage(Integer commanId) throws Exception { 
    	clientSendMessage(commanId);
//    	serverSendMessage(commanId);
    	return success(true);
	}

	//未加入权限表
	@RequestMapping("/client/send/message")
	public ResponseEntity<Object>  clientSendMessage(Integer commanId) throws Exception { 
		TblPointSendCommandEntity commandDefault = (TblPointSendCommandEntity)crudService.get(commanId, TblPointSendCommandEntity.class, true);
		TblPointsEntity pointDefine = pointDefineService.getByRegistPackageOrId(commandDefault.getPointId(), null); 
		if(pointDefine==null ) {
			throw new MyDefineException(PoinErrorType.POINT_NOT_EXIST);
		}
		//给机器的ip发送温度采集指令
		if( StringUtil.isEmpty(pointDefine.getIp()) ) {
			throw new MyDefineException(PoinErrorType.POINT_IP_IS_NULL);
		}
		//是否已经有设备 正在等待接收温度指令返回
		if(!canSendRecord(pointDefine.getIp())) { 
			throw new MyDefineException(PoinErrorType.LOCK_POINT_SEND_RECORD);
		}
		TblCommandRecordEntity update = recordService.updateClientRecord(commandDefault,pointDefine.getIp()); 
		nettyService.clientSendMessage(pointDefine.getIp(), commandDefault.getCommandContent()); 
		return success(true);
	}

	//主动发送，有定时任务被动发送
	@RequestMapping("/server/send/message")
	public ResponseEntity<Object>  serverSendMessage(Integer commanId) throws Exception { 
		TblPointSendCommandEntity commandDefault = (TblPointSendCommandEntity)crudService.get(commanId, TblPointSendCommandEntity.class, true);
		TblPointsEntity pointDefine = pointDefineService.getByRegistPackageOrId(commandDefault.getPointId(), null); 
		if(pointDefine==null ) {
			throw new MyDefineException(PoinErrorType.POINT_NOT_EXIST);
		} 
		//通过注册包给设备发送 电流电压的采集指令
		if( StringUtil.isEmpty(pointDefine.getPointRegistPackage()) ) {
			throw new MyDefineException(PoinErrorType.POINT_REGISTPACKAGE_IS_NULL);
		}
		if(!canSendRecord(pointDefine.getPointRegistPackage())) { 
			throw new MyDefineException(PoinErrorType.LOCK_POINT_SEND_RECORD);
		}
		TblCommandRecordEntity update = recordService.updateServerRecord(commandDefault,pointDefine.getPointRegistPackage()); 
		nettyService.serverSendMessage(pointDefine.getPointRegistPackage(), commandDefault.getCommandContent()); 
		return success(true);
	}

	public static  boolean canSendRecord(String registPackage) {
		if( map==null ) {
			return true;
		}
		Integer recordId = map.get(registPackage); 
		if(recordId==null ) {
			return true ; 
		}
		//时间计算如果超过3秒还没接收到，就去掉该锁
		ICommandRecordService recordService = SpringUtil.getBean(ICommandRecordService.class);
		CommandRecordDetail commandRecordDetail = recordService.getList(recordId, registPackage, null).get(0); 
		Long lastRecordTime = commandRecordDetail.getRecord().getRecordTime().getTime();
		Long now = new Date().getTime();
		long seconds =  ((now-lastRecordTime)/1000);
		if(seconds>=7) {
			return true;
		}
		return false;
	}
	  
	
	  
	
	
}
