package com.changlan.netty.udpserver;

import com.changlan.common.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket>  {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.isActive()); 
		String receiveMsg = msg.content().toString(CharsetUtil.UTF_8);
		System.out.println(receiveMsg);
		
//			ByteBuf buffer = Unpooled.buffer(7);
//			buffer.writeShort(0);
//			buffer.writeShort(1234);
//			buffer.writeByte(1);
//			buffer.writeByte(0);
//			
//			DatagramPacket dp = new DatagramPacket(buffer, msg.sender());
			//handlerMessage(new SessionImpl(ctx.channel()), dataPacket);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

	}

	

}
