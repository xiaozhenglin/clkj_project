package com.changlan.common.util;

import org.smslib.*;
import org.smslib.Service.ServiceStatus;
import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;
import org.smslib.modem.SerialModemGateway;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

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

    /**
     * 短信发送
     * @param receivePhones 接收手机号
     * @param sendContent 发送内容
     * @return 返回结果
     */
    public  boolean sendSMS(String[] receivePhones,String sendContent) throws Exception{
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
        	 portId = (CommPortIdentifier) portList.nextElement();
        	 if(portId!=null) {
        		 portName = portId.getName();
        	 }
        }
        if(portName != null&& portName!=""){
           OutboundNotification outboundNotification = new OutboundNotification();
           /*
            modem.com3:网关ID（即短信猫端口编号）
            COM3:串口名称（在window中以COMXX表示端口名称，在linux,unix平台下以ttyS0-N或ttyUSB0-N表示端口名称），通过端口检测程序得到可用的端口
            115200：串口每秒发送数据的bit位数,必须设置正确才可以正常发送短信，可通过程序进行检测。常用的有115200、9600
            Huawei：短信猫生产厂商，不同的短信猫生产厂商smslib所封装的AT指令接口会不一致，必须设置正确.常见的有Huawei、wavecom等厂商
            最后一个参数表示设备的型号，可选
            */
            SerialModemGateway gateway  = new SerialModemGateway("modem."+portName.toLowerCase(),portName,115200,"Wavecom","");
            gateway.setInbound(true); //设置true，表示该网关可以接收短信
            gateway.setOutbound(true); // 设置true，表示该网关可以发送短信

            Service service = Service.getInstance();            /**-----创建发送短信的服务（它是单例的）------**/
            ServiceStatus serviceStatus = service.getServiceStatus();
            System.out.println(serviceStatus.toString()); 
            
            if(serviceStatus!=ServiceStatus.STARTED&&serviceStatus!=ServiceStatus.STARTING) {
            	service.addGateway(gateway);/***-----将网关添加到短信猫服务中*-----**/
            	service.startService(); /**-----启动服务------**/
            	Service.getInstance().setOutboundMessageNotification(outboundNotification); /**-----发送短信成功后的回调函方法-----**/
            }
            
            /**-----发送短信------**/
            for(int i=0;i<receivePhones.length;i++){
                if(receivePhones[i] != null && !receivePhones[i].equals("")){
                    OutboundMessage msg = new OutboundMessage(receivePhones[i],sendContent);
                    msg.setEncoding(Message.MessageEncodings.ENCUCS2);
                    boolean sendMessage = service.sendMessage(msg);
                    System.out.println("是否发送成功"+sendMessage); 
                   //System.out.println("短信发送内容：----"+sendContent);
                }
            }
            return true;
//                /**-----关闭服务------**/
//                service.stopService();
        }else {
            System.out.println("短信猫的串口没有检测到，请检查！！！");
            return false;
        }
    }

    public class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
        	System.out.println("回调"+msg.getErrorMessage());
        }
    }


    public static void main(String[] args) throws Exception{
        /**
           	* 短信发送测试
         */
        GsmCat obj = new GsmCat();
        for(int i = 0; i< 2;i++) {
        	 obj.sendSMS(new String[]{"18390820674"}," 短信猫给你发了一条短息  !");
             Thread.sleep(2000);
             obj.sendSMS(new String[]{"18390820674"}," 短信猫给你发了一条短息2  !");
             Thread.sleep(2000);
             obj.sendSMS(new String[]{"18390820674"}," 短信猫给你发了一条短息3  !");
        }
    }

}
