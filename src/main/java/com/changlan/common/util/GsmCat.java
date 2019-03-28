package com.changlan.common.util;

import org.smslib.*;
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

    static CommPortIdentifier portId;
    static Enumeration portList;
    static int bauds[] = { 9600, 19200, 57600, 115200 };    //检测端口所支持的波特率
    static String portName = null; //检测到的串口
    static int portBaud = 0; //检测到的串口波特率

    /**
     * 校验短信猫与之匹配的串口、波特率
     */
    public void getCommonPort(){
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements())
        {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
               //System.out.println("找到串口: " + portId.getName());
                for (int i = 0; i < bauds.length; i++)
                {
                    System.out.print("  Trying at " + bauds[i] + "...");
                    try
                    {
                        SerialPort serialPort;
                        InputStream inStream;
                        OutputStream outStream;
                        int c;
                        String response;
                        serialPort = (SerialPort) portId.open("SMSLibCommTester", 1971);
                        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
                        serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        inStream = serialPort.getInputStream();
                        outStream = serialPort.getOutputStream();
                        serialPort.enableReceiveTimeout(1000);
                        c = inStream.read();
                        while (c != -1)
                            c = inStream.read();
                        outStream.write('A');
                        outStream.write('T');
                        outStream.write('\r');
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch (Exception e)
                        {
                        }
                        response = "";
                        c = inStream.read();
                        while (c != -1)
                        {
                            response += (char) c;
                            c = inStream.read();
                        }
                        if (response.indexOf("OK") >= 0)
                        {
                            try
                            {
                                System.out.print("  获取设备信息...");
                                outStream.write('A');
                                outStream.write('T');
                                outStream.write('+');
                                outStream.write('C');
                                outStream.write('G');
                                outStream.write('M');
                                outStream.write('M');
                                outStream.write('\r');
                                response = "";
                                c = inStream.read();
                                while (c != -1)
                                {
                                    response += (char) c;
                                    c = inStream.read();
                                }
                                portName = portId.getName();
                                portBaud = bauds[i];
                               //System.out.println("  发现设备: " + response.replaceAll("\\s+OK\\s+", "").replaceAll("", "").replaceAll("\r", ""));
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                               //System.out.println("  没有发现设备!");
                            }
                        }
                        else//System.out.println("  没有发现设备!");
                        serialPort.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                       //System.out.println("  没有发现设备!");
                    }
                }
            }
        }
    }

    /**
     * 短信发送
     * @param receivePhones 接收手机号
     * @param sendContent 发送内容
     * @return 返回结果
     */
    public  boolean sendSMS(String[] receivePhones,String sendContent){
        getCommonPort();//查找短信猫对应的串口和波特率
        // 1、连接网关的id
        // 2、com口名称，如COM1或/dev/ttyS1（根据实际情况修改）
        // 3、串口波特率，如9600（根据实际情况修改）
        // 4、开发商，如Apple
        // 5、型号，如iphone4s
        if(portName != null){
           OutboundNotification outboundNotification = new OutboundNotification();
           /*
            modem.com1:网关ID（即短信猫端口编号）
            COM4:串口名称（在window中以COMXX表示端口名称，在linux,unix平台下以ttyS0-N或ttyUSB0-N表示端口名称），通过端口检测程序得到可用的端口
            115200：串口每秒发送数据的bit位数,必须设置正确才可以正常发送短信，可通过程序进行检测。常用的有115200、9600
            Huawei：短信猫生产厂商，不同的短信猫生产厂商smslib所封装的AT指令接口会不一致，必须设置正确.常见的有Huawei、wavecom等厂商
            最后一个参数表示设备的型号，可选
            */
            SerialModemGateway gateway  = new SerialModemGateway("modem."+portName.toLowerCase(),portName,portBaud,"wavecom","");
            gateway.setInbound(true);//设置true，表示该网关可以接收短信
            gateway.setOutbound(true);// 设置true，表示该网关可以发送短信
            try {
                /**-----创建发送短信的服务（它是单例的）------**/
                Service service = Service. getInstance();
                /**-----发送短信成功后的回调函方法-----**/
                Service.getInstance().setOutboundMessageNotification(outboundNotification);
                /***-----将网关添加到短信猫服务中*-----**/
                service.addGateway(gateway);
                /**-----启动服务------**/
                service.startService();
                /**-----发送短信------**/
                for(int i=0;i<receivePhones.length;i++){
                    if(receivePhones[i] != null && !receivePhones[i].equals("")){
                        OutboundMessage msg = new OutboundMessage(receivePhones[i],sendContent);
                        msg.setEncoding(Message.MessageEncodings.ENCUCS2);
                        service.sendMessage(msg);
                       //System.out.println("短信发送内容：----"+sendContent);
                    }
                }
                /**-----关闭服务------**/
                service.stopService();
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }else {
           //System.out.println("短信猫的串口没有检测到，请检查！！！");
            return false;
        }
    }

    public class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
        }
    }

    public static void main(String[] args){
        /**
         * 短信发送测试
         */
        GsmCat obj = new GsmCat();
        boolean flag = obj.sendSMS(new String[]{"18390820674"},"Is OK !");
       //System.out.println(flag);
    }

}
