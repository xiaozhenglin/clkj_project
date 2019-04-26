package com.changlan.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;

import com.changlan.common.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * descripiton: 客户端
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 16:40
 * @modifier:
 * @since:
 */
public class ModbusClient {

    private String ip ;

    private int port ;

    private boolean stop = false;

    public ModbusClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ModbusClient() {
		// TODO Auto-generated constructor stub
	}
    
    private  Channel connectChannel  ;

	public void run() throws IOException {
        //设置一个多线程循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //启动附注类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        //指定所使用的NIO传输channel
        bootstrap.channel(NioSocketChannel.class);
        //指定客户端初始化处理
        bootstrap.handler(new ClientIniterHandler());
        try {
            //连接服务
            Channel channel = bootstrap.connect(ip, port).sync().channel();
            this.connectChannel =  channel;

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e); 
//            System.exit(1);
        } 
//        finally {
//            workerGroup.shutdownGracefully();
//        }
    }

	
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public Channel getConnectChannel() {
		return connectChannel;
	}

	public void setConnectChannel(Channel connectChannel) {
		this.connectChannel = connectChannel;
	}

	public static void main(String[] args) throws Exception { 
		
//		String host = "http://clkj.ngrok.xiaomiqiu.cn";
//		host="192.168.1.199";
//		nettyClient.run();
		
//		测试用
//		Date now = new Date();
    	for(int i =0 ; i<1;i++) {
    		ModbusClient nettyClient = new ModbusClient("127.0.0.1",502); 
    		nettyClient.run();
    		Channel connectChannel = nettyClient.getConnectChannel(); 
    		System.out.println(connectChannel.id()+"--"+connectChannel.isActive());
    		ByteBuf buf = Unpooled.buffer(300);
        	byte[] bytes =  StringUtil.hexStringToBytes("000000000006640300010001");
    	 	connectChannel.writeAndFlush(buf.writeBytes(bytes)); 
//        	ByteBuffer buf = ByteBuffer.allocate(60);
//			buf.put(0, (byte) 0);
//			buf.put(1, (byte) 0);
//			buf.put(2, (byte) 0);
//			buf.put(3, (byte) 0);
//			buf.put(4, (byte) 0);
//			buf.put(5, (byte) 6);
//			buf.put(6, (byte) 100);
//			buf.put(7, (byte) 3);
//			buf.put(8, (byte) 0);
//			buf.put(9, (byte) 1);
//			buf.put(10, (byte) 0);
//			buf.put(11, (byte) 1);
//			connectChannel.writeAndFlush(buf.writeBytes(buf)); 
    	}
//
//		Date now2 = new Date();
//		long time = now2.getTime();long time2 = now.getTime();
//		long seconds = (time-time2);
//		System.out.println(seconds);
		
    }
	
	
}
