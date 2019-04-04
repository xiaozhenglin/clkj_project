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

import com.changlan.common.pojo.MyDefineException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Auther: Ningsc
 * @Date: 2018/6/26 16:38
 * @Description: 短信猫发送工具
 * http://www.inextera.com/thread-1216-1-1.html
 */
@Component
public class GsmCat {

    static CommPortIdentifier portId;//单个串口
    static Enumeration portList;//串口列表
//    static int bauds[] = { 9600, 19200, 57600, 115200 };    //检测端口所支持的波特率
    static String portName = null; //检测到的串口
//    static int portBaud = 0; //检测到的串口波特率

    
    public Service initService() throws Exception { 
    	portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
        	 portId = (CommPortIdentifier) portList.nextElement();
        	 if(portId!=null) {
        		 portName = portId.getName();
        	 }
        }
        if(portName != null&& portName!=""){
            Service service = Service.getInstance();            /**-----创建发送短信的服务（它是单例的）------**/
            ServiceStatus serviceStatus = service.getServiceStatus();
            System.out.println(serviceStatus.toString()); 
            
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
        		
        		
                SerialModemGateway gateway  = new SerialModemGateway("modem."+portName.toLowerCase(),portName,115200,"Wavecom","");
                gateway.setInbound(true); //设置true，表示该网关可以接收短信
                gateway.setOutbound(true); // 设置true，表示该网关可以发送短信
            	service.addGateway(gateway);/***-----将网关添加到短信猫服务中*-----**/
            	service.startService(); /**-----启动服务------**/
            	service.setOutboundMessageNotification(outboundNotification); /**-----发送短信成功后的回调函方法-----**/
            }
            return service;
        }else {
        	throw  new  MyDefineException("SMS001", "没有可用端口", false, null);
        }
    }
    
    /**
     * 短信发送
     * @param receivePhones 接收手机号
     * @param sendContent 发送内容
     * @return 返回结果
     */
    public  void sendSMS(String[] receivePhones,String sendContent) throws Exception{
		Service service = initService();
        /**-----发送短信------**/
        for(int i=0;i<receivePhones.length;i++){
            if(receivePhones[i] != null && !receivePhones[i].equals("")){
                OutboundMessage msg = new OutboundMessage(receivePhones[i],sendContent);
                msg.setEncoding(Message.MessageEncodings.ENCUCS2);
                boolean sendMessage = service.sendMessage(msg);
                System.out.println("是否发送成功"+sendMessage + "内容:"+sendContent); 
            }
        }
//        return true;
    }
    
    public List<InboundMessage> receiveMessage() throws Exception {
    	Service service = initService();
    	List<InboundMessage> msgList = new ArrayList<InboundMessage>(); //接受的短信类
		service.readMessages(msgList, MessageClasses.ALL);
		for (InboundMessage msg : msgList) {
			System.out.println("doIt接受文本:"+msg.getText());
		}
		return msgList;
    }

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
			if (msgType == MessageTypes.INBOUND) {
				//入站
//				System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
			}else if (msgType == MessageTypes.STATUSREPORT) {
				//状态报告
//				System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
			}
			System.out.println("InboundNotification 接受文本"+msg.getText());
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

    public static void main(String[] args) throws Exception{
        GsmCat obj = new GsmCat();
        for(int i = 0; i< 1;i++) {
        	 obj.sendSMS(new String[]{"+8614789966508"}," 短信猫给你发了一条短息1  !"+i);
             Thread.sleep(2000);
             obj.sendSMS(new String[]{"+8614789966508"}," 短信猫给你发了一条短息2  !"+i);
             Thread.sleep(2000);
             obj.sendSMS(new String[]{"+8614789966508"}," 短信猫给你发了一条短息3  !" + i);
        }
        while(true) {
        	Thread.sleep(3000);
         	obj.receiveMessage();
        }
    }

}
