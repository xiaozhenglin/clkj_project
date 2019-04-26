package com.changlan.netty.client;


import com.changlan.netty.pojo.MyDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * descripiton: 客户端处理初始化
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 16:55
 * @modifier:
 * @since:
 */
public class ClientIniterHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //注册管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decode", new MyDecoder());
        pipeline.addLast("encode", new StringEncoder());
        pipeline.addLast("chat", new ClientHandler());
    }
}
