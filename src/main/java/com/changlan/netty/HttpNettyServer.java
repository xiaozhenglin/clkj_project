package com.changlan.netty;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changlan.common.configuration.SmsCatConfiguration;
import com.changlan.common.entity.TblSystemVarEntity;
import com.changlan.common.pojo.ParamMatcher;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.netty.pojo.NettyConfiguration;
import com.changlan.netty.server.NettyServer;
import com.changlan.netty.server.ServerIniterHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpNettyServer extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(HttpNettyServer.class);
    
    public static Map<Object,Channel> messageChannelMap = new ConcurrentHashMap<Object, Channel>(); //用于弹框的消息推送

	@Override
    public void run() {
			EventLoopGroup bossGroup = new NioEventLoopGroup();
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        
	        try{
	            ServerBootstrap serverBootstrap = new ServerBootstrap();
	            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
	                           .handler(new LoggingHandler(LogLevel.INFO))
	                           .childHandler(new WebSocketChannelInitializer());
	            
	            try {
	            	ICrudService crudService = SpringUtil.getBean(ICrudService.class);
	            	Map map = new HashMap();
	            	map.clear();
	         		map.put("systemCode", new ParamMatcher("http_netty_server_port"));
	            	TblSystemVarEntity TblSystemVar  =  (TblSystemVarEntity) crudService.findOneByMoreFiled(TblSystemVarEntity.class,map,true);//系统变量得到netty_server_port
	            	String port = TblSystemVar.getSystemValue();
	            	Integer httpNettyPort =  Integer.parseInt(port);
	            	//ChannelFuture channelFuture = serverBootstrap.bind(NettyConfiguration.getHttpNettyPort()).sync();
	            	ChannelFuture channelFuture = serverBootstrap.bind(httpNettyPort).sync();
	            	//logger.info("启动http服务器端口"+NettyConfiguration.getHttpNettyPort());
	            	logger.info("启动http服务器端口"+httpNettyPort);
					channelFuture.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }finally{
	            bossGroup.shutdownGracefully();
	            workerGroup.shutdownGracefully();
	        }
	    
    }

	public static Map<Object, Channel> getMessageChannelMap() {
		return messageChannelMap;
	}

	public static void setMessageChannelMap(Map<Object, Channel> messageChannelMap) {
		NettyServer.messageChannelMap = messageChannelMap;
	}

}
