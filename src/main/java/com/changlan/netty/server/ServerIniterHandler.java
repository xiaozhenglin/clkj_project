package com.changlan.netty.server;

import java.util.concurrent.TimeUnit;

import com.changlan.netty.pojo.MyDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;


/**
 * descripiton: 服务器初始化
 */
public class ServerIniterHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //管道注册handler 
        ChannelPipeline pipeline = socketChannel.pipeline();
        //编码通道处理
        //pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,0,4));
        //pipeline.addLast("idleStateHandler", new IdleStateHandler(5, 3, 0));
       // pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS));
       // pipeline.addLast("writeTimeoutHandler", new WriteTimeoutHandler(3000, TimeUnit.MILLISECONDS));
        //pipeline.addLast("idleStateTrigger", new ServerIdleStateTrigger());
        pipeline.addLast("decode", new MyDecoder());
        //转码通道处理
        pipeline.addLast("encode", new StringEncoder());
        //聊天服务通道处理
        pipeline.addLast("chat", new ServerHandler());
    }
}
