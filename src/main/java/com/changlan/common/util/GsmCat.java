package com.changlan.common.util;

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

/**
 * @Auther: Ningsc
 * @Date: 2018/6/26 16:38
 * @Description: 短信猫发送工具
 * http://www.inextera.com/thread-1216-1-1.html
 */
@Component
public class GsmCat {
	
	public static String serverPortName = "COM3";
	public static Integer serverPortBound = 115200;
	
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
	public static Map<String,SerialModemGateway> gateWays = new HashMap();//已经添加的设备对应的 设备id
    
	//初始化多个串口和对应的波特率
    public static Service initService(List<SmsParams> list) throws Exception { 
//    	portList = CommPortIdentifier.getPortIdentifiers();
//        while (portList.hasMoreElements()) {
//        	 portId = (CommPortIdentifier) portList.nextElement();
//        	 if(portId!=null) {
//        		 portName = portId.getName();
//        	 }
//        }

    	//先前加入的设备
    	String lastPort = "";
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
		    				System.out.println("添加设备》》》》》》》"+portName); 
			                try {
			            		SerialModemGateway gateway  = new SerialModemGateway("modem."+portName.toLowerCase(),portName,portBaud,"Wavecom","");
				                gateway.setInbound(true); //设置true，表示该网关可以接收短信
				                gateway.setOutbound(true); // 设置true，表示该网关可以发送短信
			                	service.addGateway(gateway);/***-----将网关添加到短信猫服务中*-----**/
				            	lastAddGateWay.add(param);
				              	gateWays.put(portName, gateway);
							} catch (Exception e) {
								System.out.println("添加网关设备失败"+portName);
								throw new Exception("添加网关设备失败"+portName);
							}
		    			}
		    		}
	    		}
			}
      	}
        
        if(serviceStatus!=ServiceStatus.STARTED&&serviceStatus!=ServiceStatus.STARTING) {
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
        	service.startService(); /**-----启动服务------**/
        }
        return service;
    }
    
    /**
     * 短信发送
     * @param receivePhones 接收手机号
     * @param sendContent 发送内容
     * @return 返回结果
     */
    public static void sendSMS(SmsParams param,String[] receivePhones,String sendContent) throws Exception {
    	System.out.println("准备发送消息给设备》》》》" + param.getPortName()); 
    	List<SmsParams> list = new ArrayList<SmsParams>();
    	list.add(param);
		Service service = initService(list);
        for(int i=0;i<receivePhones.length;i++){
        	
            Thread.sleep(2000); //发送消息需要间隔一段时间 ，端口被占用问题。(一定要加)
            if(receivePhones[i] != null && !receivePhones[i].equals("")){
                OutboundMessage msg = new OutboundMessage(receivePhones[i],sendContent);
                msg.setEncoding(Message.MessageEncodings.ENCUCS2);
                
                SerialModemGateway gateway = gateWays.get(param.getPortName());
                if(gateway.getStatus()!=GatewayStatuses.STARTED) {
                	System.out.println(param.getPortName()+"状态不可用"+gateway.getStatus().toString());
                }else {
                	boolean sendMessage =  service.sendMessage(msg, gateway.getGatewayId());
                	System.out.println("是否发送成功: "+sendMessage ); 
                }
            }
        }
    }
    
    public List<InboundMessage> receiveMessage() throws Exception {
    	Service service = initService(null);
    	List<InboundMessage> msgList = new ArrayList<InboundMessage>(); //接受的短信类
		service.readMessages(msgList, MessageClasses.ALL);
		for (InboundMessage msg : msgList) {
			System.out.println("doIt接受消息》》"+msg.getText()+"》》来电号码:"+msg.getOriginator());
//			analysisReceiveMessage(msg.getText(), msg.getOriginator()); 
		}
		return msgList;
    }

    //出站
    public static class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
        	System.out.println("回调"+msg.getErrorMessage());
        }
    }
    
    public static class InboundNotification implements IInboundMessageNotification 
	{
		public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg)
		{
//			if (msgType == MessageTypes.INBOUND) {
				//入站
//				System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
//			}else if (msgType == MessageTypes.STATUSREPORT) {
				//状态报告
//				System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
//			}
			System.out.println("InboundNotification类接受消息》》》》》"+msg.getText());
			//保存入库
//			analysisReceiveMessage(msg.getText(), msg.getOriginator()); 
			try {
				//打印后删除  不会重复接收。
				gateway.deleteMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

    
    public static class CallNotification implements ICallNotification
	{
		public void process(AGateway gateway, String callerId)
		{
//			System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
		}
	}

	public static  class GatewayStatusNotification implements IGatewayStatusNotification
	{
		public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus)
		{
//			System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
		}
	}

	public  static class OrphanedMessageNotification implements IOrphanedMessageNotification
	{
		public boolean process(AGateway gateway, InboundMessage msg)
		{
//			System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
//			System.out.println(msg);
			// Since we are just testing, return FALSE and keep the orphaned message part.
			return false;
		}
	}
	
	
	public void stopService() throws Exception {
		Service.getInstance().stopService();
		System.out.println(">>>>>>>>>>关闭服务完成");
	}
	
	public static Map<String,SerialModemGateway> getLocalGateWay(){
		return gateWays;
	}
	
	public static String getServiceStatus(){
		return Service.getInstance().getServiceStatus().toString();
	}

	public static void analysisReceiveMessage(String receiveMsg,String phone) throws Exception { 
		if(receiveMsg.substring(0,4).equals("*CS,")){ 	
			String alm=receiveMsg.substring(11,13); //类型
			String angle=receiveMsg.substring(17,19); //具体的值
			String result="";
			IPointDefineService pointDefineService = SpringUtil.getBean(IPointDefineService.class);
			TblPointsEntity entity = new TblPointsEntity();
			entity.setSmsNumber(phone.substring(2)); //还没调试 应该要去掉前缀86什么的
			List<PointInfoDetail> all = pointDefineService.getAll(entity); 
			if(!ListUtil.isEmpty(all)) {
				PointInfoDetail pointInfoDetail = all.get(0); 
				TblPointsEntity point = pointInfoDetail.getPoint(); 
				String pointName = point.getPointName();
				if(alm.equals("A2")){
					result=pointName+"井盖发生异常，倾斜角度为："+angle;
					//发送短信给其他人
					sendMsgToOher(point.getPhones(),result);
		       	}else if(alm.equals("R1")){
		       		result=pointName+"井盖恢复正常，当前角度为："+angle;
					sendMsgToOher(point.getPhones(),result);
		       	}else if(alm.equals("T1")){
					result=pointName+"井盖周期上报，当前角度为："+angle;
					saveMsgData(point.getSmsNumber(),result,2);
				}else if(alm.equals("A3")){
					result=pointName+"井盖电量偏低，请及时更换电池。"+angle;
					sendMsgToOher(point.getPhones(),result);
				}
			}
		}
	}
	
	//记录数据
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

	//通知运维人员
	public static void sendMsgToOher(String phones, String sendContent) throws Exception { 
		// 获取需要发送的电话号码
		if(StringUtil.isNotEmpty(phones)) {
			//sms 发送消息
			String[] receivePhones = phones.split(","); 
			SmsParams param = new SmsParams(serverPortName, serverPortBound);//服务器的串口来发送
			sendSMS(param, receivePhones, sendContent);
	    	saveMsgData(phones, sendContent, 1);
		}
	}

	public static void main(String[] args) throws Exception {
//        List<SmsParams> list = new ArrayList<SmsParams>();
//        SmsParams param = new SmsParams(GsmCat.serverPortName, GsmCat.serverPortBound); //设备
//        list.add(param);
//        GsmCat.initService(list);  //添加设备，初始化启动
////        System.out.println(getServiceStatus());
//        for(int i = 0; i< 1;i++) {
//        	GsmCat.sendSMS(param,new String[]{"+8614789966508","18390820674"}," 短信猫给你发了一条短息1");
//        }
		String receiveMsg= "";
        GsmCat.analysisReceiveMessage(receiveMsg, "8614789966508");
    }

//	public static Map<Integer, Integer> getMap() {
//		return map;
//	}
//
//	public static void setMap(Map<Integer, Integer> map) {
//		GsmCat.map = map;
//	}
    
	
    
}
