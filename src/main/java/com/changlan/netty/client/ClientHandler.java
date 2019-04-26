package com.changlan.netty.client;

import java.io.IOException;
import java.net.SocketAddress;

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
	
	/**读消息*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    	Channel channel = ctx.channel(); 
    	SocketAddress remoteAddress = channel.remoteAddress();
        System.out.println("服务器消息"+ remoteAddress + "内容："+s);
    }
    
    /**
     * 创建客户端
     * */
	public ModbusClient createTcpClient(String servIP, short port) throws IOException { 
		ModbusClient nettyClient = new ModbusClient(servIP,port); 
		nettyClient.run();
		return nettyClient;
	}
	
	/**
	 * 写消息
	 */
	public void writeData(ModbusClient nettyClient,ByteBuf buf) {
//		ByteBuf和ByteBuffer不一样
		Channel connectChannel = nettyClient.getConnectChannel(); 
		connectChannel.writeAndFlush(buf); 
	}
}
