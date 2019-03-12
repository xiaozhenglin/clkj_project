package com.changlan.netty.server;

import java.util.concurrent.TimeUnit;

import com.changlan.netty.pojo.MyDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * descripiton: 服务器初始化
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 15:46
 * @modifier:
 * @since:
 */
public class ServerIniterHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //管道注册handler 
        ChannelPipeline pipeline = socketChannel.pipeline();
        //编码通道处理
        pipeline.addLast("decode", new MyDecoder());
        //转码通道处理
        pipeline.addLast("encode", new StringEncoder());
        //聊天服务通道处理
        pipeline.addLast("chat", new ServerHandler());
        
        //添加心跳检测客户端是否连接   重点部分，绝对不能删掉
        pipeline.addLast(new IdleStateHandler(30, 0, 0,TimeUnit.SECONDS));
        pipeline.addLast(new ServerHandler());
    }
}
