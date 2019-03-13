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
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.server.ServerHandler;
import com.changlan.netty.service.INettyService;
import com.changlan.point.constrant.PointConstrant;
import com.changlan.point.pojo.PoinErrorType;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.pojo.LoginUser;

@RestController
@RequestMapping("/admin/netty/server")
public class NettyController extends BaseController{
	
	@Autowired
	ICrudService crudService;
	
	@Autowired
	INettyService nettyService;
	
	@Autowired
	IPointDefineService pointDefineService;
	
	
	public static Map<String,Integer> map = new HashMap<String,Integer>();
	
    private static final Logger logger = LoggerFactory.getLogger(NettyController.class);


	//主动发送，有定时任务被动发送
	@RequestMapping("/send/message")
	@Transactional
	public ResponseEntity<Object>  sendMessage(Integer pointId ,Integer commanId,String message ) throws Exception { 
		String registPackage = saveRecord(pointId,commanId,message);
		nettyService.sendMessage(registPackage, message); 
		return success(true);
	}

	
	private String saveRecord(Integer pointId, Integer commanId, String message)  throws Exception { 
		TblPointsEntity pointDefine = pointDefineService.getByRegistPackageOrId(pointId, null); 
		String registPackage = pointDefine.getPointRegistPackage();
		if(!canSendRecord(registPackage)) {
			throw new MyDefineException(PoinErrorType.LOCK_IP_SEND_RECORD);
		}
		//保存用户操作指令
		TblCommandRecordEntity entity = new TblCommandRecordEntity();
		entity.setPointId(pointId); 
		entity.setAdminUserId(LoginUser.getCurrentUser().getAdminUserId());
		entity.setSendCommandId(commanId); 
		entity.setCommandContent(message);
		entity.setRecordTime(new Date()); 
		//将记录id保存到会话，当有返回消息时保存起来
		TblCommandRecordEntity update = (TblCommandRecordEntity)crudService.update(entity, true); 
		if(update == null) {
			logger.info("第二步注册包：registPackage：" + registPackage + "指令内容："+message + "发送失败" );
			throw new MyDefineException(PoinErrorType.SAVE_EROOR.getCode(), PoinErrorType.SAVE_EROOR.getName(), false, null);
		}
		logger.info("第二步发送指令 注册包：registPackage：" + registPackage + "指令内容："+message + "操作记录commandRecordId " + update.getCommandRecordId());
		//加锁
		map.put(registPackage, update.getCommandRecordId());
		return registPackage;
	}


	public static  boolean canSendRecord(String registPackage) {
		if( map==null || StringUtil.isEmpty(registPackage)) {
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
		if(seconds>=3) {
			return true;
		}
	
		return false;
	}
	

	public static Map<String, Integer> getMap() {
		return map;
	}

	public static void setMap(Map<String, Integer> map) {
		NettyController.map = map;
	}

	
	
	
}
