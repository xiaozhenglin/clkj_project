package com.changlan.netty.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

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
import com.changlan.common.pojo.BaseResult;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.GsmCat;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.server.NettyServer;
import com.changlan.netty.server.ServerHandler;
import com.changlan.netty.service.INettyService;
import com.changlan.other.entity.DeviceData;
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
	
	public static Map<String, Integer> map = new ConcurrentHashMap<String,Integer>();
	
    private static final Logger logger = LoggerFactory.getLogger(NettyController.class);
    
    //设置不需要权限   关闭短信猫的服务
    @RequestMapping("/close/sms/cat")
  	public ResponseEntity<Object>  closeCat() throws Exception { 
    	GsmCat instance = GsmCat.getInstance(); 
    	logger.info("短信猫服务器的状态:"+instance.getServiceStatus());
    	if(!instance.getServiceStatus().equalsIgnoreCase("STOPPING") ) {
    		instance.stopService();
    	}
      	return success(true);
  	} 
    
    //设置不需要权限   测试发送指令用
    @RequestMapping("/test/message")
	public ResponseEntity<Object>  sendMessage(Integer commanId) throws Exception { 
//    	savePartialDischarge();
//    	clientSendMessage(commanId);
    	serverSendMessage(commanId);
//    	BaseResult baseResult = new BaseResult("", "", true, "testData"); 
//    	nettyService.serverSendMessageBox(baseResult);
    	return success(true);
	}
    
//    public static List<Object> savePartialDischarge() {
//		String content = "0114B4B30601AC738C01B6756501B6773F01A4791801AC7AF2019F7CCC01B67EA601AE807E01BE825801AC843201B4860B01B287E401A889BD01A08B9601AA8D6F01B08F4901BA912201B692FB01AC94D501A496AE01A8988701AA9A6101A69C3B01A29E14019F9FEE01A2A1C8019DA3A101B2A57B01A2A755019DA92E01A6AB080190ACE1019BAEBA01ACB093019FB26C01AEB44601A6B62001A8B7F90199B9D201A0BBAB01AABD8501A8BF5F01A6C13801AEC3121AD3";
//		List<Object> result = new ArrayList<Object>();
//		String substring = content.substring(10);
//		System.out.println(substring); 
//		int i = 0 ;
//		while(i<=substring.length()-8) {
//			DeviceData data = new DeviceData();
//			String amplitude = substring.substring(i, i+4);
//			amplitude = StringUtil.decimalConvert(amplitude, 16, 10, null); 
//			System.out.println("幅值"+amplitude); 
//			String phase = substring.substring(i+4, i+8);
//			phase = StringUtil.decimalConvert(phase, 16, 10, null); 
//			System.out.println("相位"+phase); 
//			data.setChannelSettings_id(14);
//			
//			data.setAmplitude(Float.parseFloat(amplitude));//幅值
//			data.setAlarm_amplitude_frequency(Integer.parseInt(phase)); // 报警频次列没什么用，这里用来存储 相位值
//			data.setCreatetime(new Date()); 
//			ICrudService crudService = SpringUtil.getBean(ICrudService.class);
//			crudService.update(data, true);
//			result.add(data);
//	
//			i= i+8;
//		}
//		return result;
//	}


	//客户端往服务器发送消息
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
		//保存记录 并加锁
		TblCommandRecordEntity update = recordService.updateClientRecord(commandDefault,pointDefine.getIp()); 
		//执行发送
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
		commandDefault.setPointName(pointDefine.getPointName()); 
		//保存记录 并加锁
		TblCommandRecordEntity update = recordService.updateServerRecord(commandDefault,pointDefine.getPointRegistPackage()); 
		//执行发送
		nettyService.serverSendMessage(pointDefine.getPointRegistPackage(), commandDefault.getCommandContent()); 
		return success(true);
	}

	public static  boolean canSendRecord(String registPackage) {
		if( map==null ) {
			return true;
		}
		if(map.size()==0) {
			return true;
		}
		
		Integer recordId = map.get(registPackage); 
		//Integer recordId = recordIds.get(0);
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
