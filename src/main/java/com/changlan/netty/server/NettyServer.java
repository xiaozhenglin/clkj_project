package com.changlan.netty.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.changlan.netty.NettyConfiguration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * descripiton:服务端
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 15:37
 * @modifier:
 * @since:
 */
public class NettyServer extends Thread{
    /**
     * 端口
     */
    private int port;
    public static Map<Object,Channel> channelMap = new ConcurrentHashMap<Object, Channel>(); 

    public NettyServer() {
		super();
	}

	@Override
    public void run() {
        //负责连接线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //负责处理客户端i/o事件、task任务、监听任务组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //启动 NIO 服务的辅助启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        //配置 Channel
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ServerIniterHandler());
        //BACKLOG用于构造服务端套接字ServerSocket对象，
        // 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
        bootstrap.option(ChannelOption.SO_BACKLOG, 2048);
        //是否启用心跳保活机制
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            //绑定服务端口监听
            Channel channel = bootstrap.bind(NettyConfiguration.nettyPort).sync().channel(); 
            System.out.println("启动服务器端口: " + NettyConfiguration.nettyPort);
            // 这行必须要
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭事件流组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

	

}
