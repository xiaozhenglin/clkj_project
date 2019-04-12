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
	
	@Autowired
	ICommandRecordService recordService;
	
	public static Map<String,Integer> map = new HashMap<String,Integer>();
	
    private static final Logger logger = LoggerFactory.getLogger(NettyController.class);


	//主动发送，有定时任务被动发送
	@RequestMapping("/send/message")
	@Transactional
	public ResponseEntity<Object>  sendMessage(Integer commanId) throws Exception { 
		TblPointSendCommandEntity commandDefault = (TblPointSendCommandEntity)crudService.get(commanId, TblPointSendCommandEntity.class, true);
		TblPointsEntity pointDefine = pointDefineService.getByRegistPackageOrId(commandDefault.getPointId(), null); 
		if(pointDefine==null ) {
			throw new MyDefineException(PoinErrorType.POINT_NOT_EXIST);
		}
		if( StringUtil.isEmpty(pointDefine.getPointRegistPackage()) ) {
			throw new MyDefineException(PoinErrorType.POINT_REGISTPACKAGE_IS_NULL);
		}
		if(!canSendRecord(pointDefine.getPointRegistPackage())) { 
			throw new MyDefineException(PoinErrorType.LOCK_POINT_SEND_RECORD);
		}
		TblCommandRecordEntity update = recordService.update(commandDefault,pointDefine.getPointRegistPackage()); 
		nettyService.sendMessage(pointDefine.getPointRegistPackage(), commandDefault.getCommandContent()); 
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
