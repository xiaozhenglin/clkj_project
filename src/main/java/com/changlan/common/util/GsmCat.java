package com.changlan.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smslib.*;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.Service.ServiceStatus;
import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;
import org.smslib.modem.SerialModemGateway;
import org.springframework.stereotype.Component;

import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.command.service.ICommandRecordService;
import com.changlan.common.configuration.SmsCatConfiguration;
import com.changlan.common.entity.TblAdminUserEntity;
import com.changlan.common.entity.TblMsgDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.SmsParams;
import com.changlan.common.service.ICrudService;
import com.changlan.point.pojo.PointInfoDetail;
import com.changlan.point.service.IPointDefineService;
import com.changlan.user.pojo.LoginUser;
import com.changlan.user.pojo.MsgType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

/**
 * @Auther: Ningsc
 * @Date: 2018/6/26 16:38
 * @Description: 短信猫发送工具
 * http://www.inextera.com/thread-1216-1-1.html
 */
@Component
public class GsmCat {
	
	public static Logger logger = LoggerFactory.getLogger(GsmCat.class);
	
	public static GsmCat cat = new GsmCat();
	
	public static GsmCat getInstance(){
		return cat ; 
	}
	
//    static CommPortIdentifier portId;//单个串口
//    static Enumeration portList;//串口列表
////    static int bauds[] = { 9600, 19200, 57600, 115200 };    //检测端口所支持的波特率
//    static String portName = null; //检测到的串口
//    static int portBaud = 0; //检测到的串口波特率
	
	
//	public static Map<Integer,Integer> map = new HashMap<Integer,Integer>(); // 同一个监控点 只能发一条
//	
//	public static  boolean canSendRecord(Integer pointId) {
//		if( map==null || pointId == null) {
//			return true;
//		}
//		Integer recordId = map.get(pointId); 
//		if(recordId==null ) {
//			return true ; 
//		}
//		//时间计算如果超过3秒还没接收到，就去掉该锁
//		ICommandRecordService recordService = SpringUtil.getBean(ICommandRecordService.class);
//		CommandRecordDetail commandRecordDetail = recordService.getList(recordId, null, null).get(0); 
//		Long lastRecordTime = commandRecordDetail.getRecord().getRecordTime().getTime();
//		Long now = new Date().getTime();
//		long seconds =  ((now-lastRecordTime)/1000);
//		if(seconds>=7) {
//			return true;
//		}
//	
//		return false;
//	}
	
	
	public static List<SmsParams> lastAddGateWay = new ArrayList<SmsParams>(); //已经添加的设备
	public static Map<String,SerialModemGateway> gateWays = new HashMap();//已经添加的设备对应的 设备网关
    
	//初始化多个串口和对应的波特率
    public static Service initService(List<SmsParams> list)  { 
//    	portList = CommPortIdentifier.getPortIdentifiers();
//        while (portList.hasMoreElements()) {
//        	 portId = (CommPortIdentifier) portList.nextElement();
//        	 if(portId!=null) {
//        		 portName = portId.getName();
//        	 }
//        }

    	//先前加入的设备
    	String lastPort = ""; //端口名称
    	if(!ListUtil.isEmpty(lastAddGateWay)) {
    		for(SmsParams param : lastAddGateWay) {
    			String portName = param.getPortName(); 
        		lastPort+=","+portName+",";
    		}
    	}

    	Service service = Service.getInstance();            /**-----创建发送短信的服务（它是单例的）------**/
        ServiceStatus serviceStatus = service.getServiceStatus();
//        System.out.println(serviceStatus.toString()); 
        
        //新入的设备
      	if(!ListUtil.isEmpty(list)) {
	    	for(SmsParams param : list) {
				String portName = param.getPortName(); 
	    		int portBaud = param.getPortBaud(); 
	    		if(portName != null&& portName!="" && portBaud>0){
	    			//没有添加过设备或者  不包含新的设备
		    		if(StringUtil.isEmpty(lastPort) || lastPort.toString().indexOf(portName) <-1) {
		    			//这里设置只有启动前才能添加设备，启动后不能添加。
		    			if(serviceStatus!=ServiceStatus.STARTED&&serviceStatus!=ServiceStatus.STARTING) {
		    				logger.info("添加设备》》》》》》》"+portName); 
			                try {
			            		SerialModemGateway gateway  = new SerialModemGateway("modem."+portName.toLowerCase(),portName,portBaud,"Wavecom","");
				                gateway.setInbound(true); //设置true，表示该网关可以接收短信
				                gateway.setOutbound(true); // 设置true，表示该网关可以发送短信
			                	service.addGateway(gateway);/***-----将网关添加到短信猫服务中*-----**/
				            	lastAddGateWay.add(param);
				              	gateWays.put(portName, gateway);
							} catch (Exception e) {
								logger.info("添加网关设备失败"+portName);
							}
		    			}
		    		}
	    		}
			}
      	}
        
        if(serviceStatus!=ServiceStatus.STARTED&&serviceStatus!=ServiceStatus.STARTING) {
        	//配置项
        	InboundNotification inboundNotification = new InboundNotification();
        	CallNotification callNotification = new CallNotification();
    		GatewayStatusNotification statusNotification = new GatewayStatusNotification();
    		OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
            OutboundNotification outboundNotification = new OutboundNotification();
        	service.setInboundMessageNotification(inboundNotification); //入站消息回调
    		service.setCallNotification(callNotification); //不清楚
    		service.setGatewayStatusNotification(statusNotification);//设备状态
    		service.setOrphanedMessageNotification(orphanedMessageNotification);
        	service.setOutboundMessageNotification(outboundNotification); /**-----发送短信成功后的回调函方法-----**/
        	try {
            	service.startService(); /**-----启动服务------**/
			} catch (Exception e) {
				logger.info("启动短信猫失败");
				return null;
			}

        }
        return service;
    }
    
    
	//通知运维人员
	public static Boolean sendMsgToOher(String phones, String sendContent)  { 
		if(StringUtil.isNotEmpty(phones)) {
			//获取需要发送的电话号码
			String[] receivePhones = phones.split(","); 
			//设置短信猫设备
			SmsParams param = new SmsParams(SmsCatConfiguration.serverPortName, Integer.parseInt(SmsCatConfiguration.serverPortBound));//服务器的串口来发送
			try {
				boolean sendSMS = sendSMS(param, receivePhones, sendContent); 
				return sendSMS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
    
    /**
     * 短信发送
     * @param receivePhones 接收手机号
     * @param sendContent 发送内容
     * @return 返回结果
     */
    public static boolean sendSMS(SmsParams param,String[] receivePhones,String sendContent) throws Exception {
    	logger.info("准备发送消息》》》》使用设备串口" + param.getPortName()); 
    	List<SmsParams> list = new ArrayList<SmsParams>();
    	list.add(param);
		Service service = initService(list);
		if(service != null ) {
			for(int i=0;i<receivePhones.length;i++){
	            Thread.sleep(2000); //发送消息需要间隔一段时间 ，端口被占用问题。(一定要加)
	            if(receivePhones[i] != null && !receivePhones[i].equals("")){
	                OutboundMessage msg = new OutboundMessage(receivePhones[i],sendContent);
	                msg.setEncoding(Message.MessageEncodings.ENCUCS2);
	                
	                SerialModemGateway gateway = gateWays.get(param.getPortName());
	                if(gateway.getStatus()!=GatewayStatuses.STARTED) {
	                	logger.info(param.getPortName()+"状态不可用"+gateway.getStatus().toString());
	                }else {
	                	boolean result =  service.sendMessage(msg, gateway.getGatewayId());
	                	logger.info("是否发送成功: "+result ); 
	                	return result;
	                }
	            }
		    }
		}
		return false;
    }
    
    //出站
    public static class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
        	System.out.println("回调"+msg.getErrorMessage());
        }
    }
    
    /**
     * 消息入站
     * */
    public static class InboundNotification implements IInboundMessageNotification 
	{
		public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg)
		{
			logger.info("InboundNotification类接受消息》》》》》"+msg.getText());
			//保存入库
			try {
				analysisReceiveMessage(msg.getText().toString(), msg.getOriginator().toString()); 
				//打印后删除  不会重复接收。
				gateway.deleteMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
    
    /**自定义接收消息
     * */
    public List<InboundMessage> receiveMessage() throws Exception {
    	Service service = initService(null);
    	List<InboundMessage> msgList = new ArrayList<InboundMessage>(); //接受的短信类
		service.readMessages(msgList, MessageClasses.ALL);
		for (InboundMessage msg : msgList) {
			logger.info("receiveMessage接受消息》》"+msg.getText()+"》》来电号码:"+msg.getOriginator());
			analysisReceiveMessage(msg.getText(), msg.getOriginator()); 
		}
		return msgList;
    }

	
	public void stopService() throws Exception {
		Service.getInstance().stopService();
		logger.info(">>>>>>>>>>关闭服务完成");
	}
	
	public static Map<String,SerialModemGateway> getLocalGateWay(){
		return gateWays;
	}
	
	public static String getServiceStatus(){
		return Service.getInstance().getServiceStatus().toString();
	}

	/**解析接受的数据*/
	public static void analysisReceiveMessage(String receiveMsg,String smsNumber) throws Exception { 
		if(receiveMsg.substring(0,4).equals("*CS,")){ 	
			String alm=receiveMsg.substring(11,13); //类型
			String angle=receiveMsg.substring(17,19); //具体的值
			String result="";
			IPointDefineService pointDefineService = SpringUtil.getBean(IPointDefineService.class);
			TblPointsEntity entity = new TblPointsEntity();
			if(smsNumber.substring(0,2).equalsIgnoreCase("86")) {
				entity.setSmsNumber(smsNumber.substring(2)); //设置电话号码
			}else {
				entity.setSmsNumber(smsNumber);
			}
			List<PointInfoDetail> all = pointDefineService.getAll(entity); 
			if(!ListUtil.isEmpty(all)) {
				PointInfoDetail pointInfoDetail = all.get(0);  //找到对应的监控点
				TblPointsEntity point = pointInfoDetail.getPoint(); 
				String pointName = point.getPointName();
				if(alm.equals("A2")){
					result=pointName+"井盖发生异常，倾斜角度为："+angle; //生成报警短信
					//发送短信给其他人
					saveMsgData(point.getSmsNumber(),result,2);
					sendMsgToOher(point.getPhones(),result);
					saveMsgData(point.getPhones(),result,1);
		       	}else if(alm.equals("R1")){
		       		result=pointName+"井盖恢复正常，当前角度为："+angle;
		       		saveMsgData(point.getSmsNumber(),result,2);
					sendMsgToOher(point.getPhones(),result);
					saveMsgData(point.getPhones(),result,1);
		       	}else if(alm.equals("T1")){
					result=pointName+"井盖周期上报，当前角度为："+angle;
					saveMsgData(point.getSmsNumber(),result,2);
					
				}else if(alm.equals("A3")){
					result=pointName+"井盖电量偏低，请及时更换电池。当前角度为："+angle;
					saveMsgData(point.getSmsNumber(),result,2); 
					sendMsgToOher(point.getPhones(),result);
					saveMsgData(point.getPhones(),result,1);
				}
			}
		}
	}
	

	/**记录数据*/
	@Transactional
	public static void saveMsgData(String phones, String content,Integer direction) {
    	ICrudService crudService = SpringUtil.getICrudService();
		TblMsgDataEntity msgData = new TblMsgDataEntity();
		msgData.setSendTime(new Date());
		msgData.setContent(content);
		msgData.setDirection(direction);
		msgData.setMsgType(MsgType.SMS_CAT.toString());
		msgData.setPhoneOrEmail(phones);
		crudService.update(msgData, true);
	}
	
	
	  
    public static class CallNotification implements ICallNotification
	{
		public void process(AGateway gateway, String callerId)
		{
		}
	}

	public static  class GatewayStatusNotification implements IGatewayStatusNotification
	{
		public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus)
		{
		}
	}

	public  static class OrphanedMessageNotification implements IOrphanedMessageNotification
	{
		public boolean process(AGateway gateway, InboundMessage msg)
		{
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
//		String receiveMsg= "";
//        GsmCat.analysisReceiveMessage(receiveMsg, "8614789966508");
    }

	
    
}
