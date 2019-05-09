package com.changlan.netty.udpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changlan.netty.pojo.NettyConfiguration;
import com.changlan.netty.server.ServerIniterHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpNettyServer extends Thread{
	
	 private Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Override
	 public void run() {
		 EventLoopGroup group = new NioEventLoopGroup();
		 try {
			 Bootstrap b = new Bootstrap();//和tcp 不一样
             b.group(group)
              .channel(NioDatagramChannel.class) //和tcp 不一样
              .option(ChannelOption.SO_BROADCAST, true)
              .handler(new UdpServerHandler());	//和tcp 不一样
             b.bind(1112).sync().channel().closeFuture();
             logger.info("启动tcp服务器端口: " + 1112);
             
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 } finally {
//            group.shutdownGracefully();
		 }
	 }

	public static void main(String[] args) {
		new UdpNettyServer().start();
	} 
	 
}
