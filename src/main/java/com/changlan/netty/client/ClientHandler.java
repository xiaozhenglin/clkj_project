package com.changlan.netty.client;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changlan.common.util.SpringUtil;
import com.changlan.netty.controller.ConnectClients;
import com.changlan.netty.event.CommandCallBackEvent;
import com.changlan.netty.server.NettyServer;
import com.changlan.netty.server.ServerHandler;
import com.changlan.netty.service.INettyService;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * descripiton: 客户端逻辑处理
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 16:50
 * @modifier:
 * @since:
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {
	
    protected static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	/**读消息*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    	Channel channel = ctx.channel(); 
    	SocketAddress remoteAddress = channel.remoteAddress();
        System.out.println("服务器消息"+ remoteAddress + "内容："+s);

        //通道 找到 对应的设备ip -> 记录id
     	String ip = getIpByChannel(channel); 
        INettyService nettyService = SpringUtil.getBean(INettyService.class);
 		Integer commandRecordId = nettyService.saveReturnMessage(ip,s);  
 		logger.info("第三步：[" + channel.remoteAddress() + "]保存返回数据 "+s+ "到操作记录的commandRecordId："+commandRecordId);
 		if(commandRecordId!=null) {
 			//开启解析事件
 			SpringUtil.getApplicationContext().publishEvent(new CommandCallBackEvent(commandRecordId,ip,s));
 		}
    }
    
    private String getIpByChannel(Channel channel) {
        Map<String, Channel> channelMap = ConnectClients.clients;
        Iterator<Entry<String, Channel>> iterator = channelMap.entrySet().iterator();
        while(iterator.hasNext()) {
         	Entry<String, Channel> next = iterator.next(); 
       	    if(next.getValue()== channel ) {
       		   return next.getKey().toString();
       	    }
        }
        return null;
 	}
}
