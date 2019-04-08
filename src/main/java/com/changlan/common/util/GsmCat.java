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
import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.pojo.SmsParams;

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
//	public static  boolean canSendRecord(String registPackage) {
//		if( map==null || StringUtil.isEmpty(registPackage)) {
//			return true;
//		}
//		Integer recordId = map.get(registPackage); 
//		if(recordId==null ) {
//			return true ; 
//		}
//		//时间计算如果超过3秒还没接收到，就去掉该锁
//		ICommandRecordService recordService = SpringUtil.getBean(ICommandRecordService.class);
//		CommandRecordDetail commandRecordDetail = recordService.getList(recordId, registPackage, null).get(0); 
//		Long lastRecordTime = commandRecordDetail.getRecord().getRecordTime().getTime();
//		Long now = new Date().getTime();
//		long seconds =  ((now-lastRecordTime)/1000);
//		if(seconds>=7) {
//			return true;
//		}
//	
//		return false;
//	}
//	
	
	
	
	public static List<SmsParams> lastAddGateWay = new ArrayList<SmsParams>(); //已经添加的设备
	public static Map<String,SerialModemGateway> gateWays = new HashMap();//已经添加的设备对应的 设备id
    
	//初始化多个串口和对应的波特率
    public Service initService(List<SmsParams> list) throws Exception { 
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
    public  void sendSMS(SmsParams param,String[] receivePhones,String sendContent) throws Exception{
    	System.out.println("准备发送消息给设备》》》》" + param.getPortName()); 
    	List<SmsParams> list = new ArrayList<SmsParams>();
    	list.add(param);
		Service service = initService(list);
        for(int i=0;i<receivePhones.length;i++){
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
    
//    public List<InboundMessage> receiveMessage() throws Exception {
//    	Service service = initService(null);
//    	List<InboundMessage> msgList = new ArrayList<InboundMessage>(); //接受的短信类
//		service.readMessages(msgList, MessageClasses.ALL);
//		for (InboundMessage msg : msgList) {
//			System.out.println("doIt接受文本:"+msg.getText());
//		}
//		return msgList;
//    }

    //出站
    public class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
        	System.out.println("回调"+msg.getErrorMessage());
        }
    }
    
    public class InboundNotification implements IInboundMessageNotification 
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
			
			try {
				//打印后删除  不会重复接收。
				gateway.deleteMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

    
    public class CallNotification implements ICallNotification
	{
		public void process(AGateway gateway, String callerId)
		{
//			System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
		}
	}

	public class GatewayStatusNotification implements IGatewayStatusNotification
	{
		public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus)
		{
//			System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
		}
	}

	public class OrphanedMessageNotification implements IOrphanedMessageNotification
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

    public static void main(String[] args) throws Exception{
        GsmCat obj = new GsmCat();
        List<SmsParams> list = new ArrayList<SmsParams>();
        SmsParams param = new SmsParams("COM3", 115200); //设备
//        SmsParams param2 = new SmsParams("COM1", 115200); //设备一定要先用测试设备调试可用
        list.add(param);
//        list.add(param2);
        Service initService = obj.initService(list); 
        System.out.println(getServiceStatus());
        for(int i = 0; i< 1;i++) {
        	 obj.sendSMS(param,new String[]{"+8614789966508","18309820674"}," 短信猫给你发了一条短息1");
             Thread.sleep(2000);
             obj.sendSMS(param,new String[]{"+8614789966508","18309820674"}," 短信猫给你发了一条短息2  ");
             Thread.sleep(3000);
             obj.sendSMS(param,new String[]{"+8614789966508","18309820674"}," 短信猫给你发了一条短息3  " );
        }
//        obj.receiveMessage();
//        obj.stopService();
    }

}
