package com.changlan.netty.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changlan.common.pojo.PointStatus;
import com.changlan.common.util.SessionUtil;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.changlan.netty.controller.NettyController;
import com.changlan.netty.event.CommandCallBackEvent;
import com.changlan.netty.event.PointRegistPackageEvent;
import com.changlan.netty.service.INettyService;
import com.changlan.point.pojo.SimplePoint;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * descripiton: 服务器的处理逻辑
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 所有的活动用户
     */
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    
    protected static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    
    
//    @Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		super.channelRead(ctx, msg);
//		if(msg.toString().substring(0,4).equalsIgnoreCase("CLKJ")) {
//			System.out.println("->>>>>>>>>>>"+msg); 
//		}else  {
//	        System.out.println("->>>>>>" +msg.toString() + "长度"+msg.toString().length()); 
//		}
//	}

	/**
     * 读取消息通道
     *
     * @param context
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext context, String s) throws Exception {
        Channel channel = context.channel();
     
        if(s.substring(0,4).equalsIgnoreCase("CLKJ")) {
        	//设置注册包
            Map<Object, Channel> channelMap = NettyServer.channelMap;
            channelMap.put(s, channel);
            NettyServer.channelMap = channelMap;
        	logger.info("第一步接受注册包 [" + channel.remoteAddress() + "]:"+ s + " 长度 "+s.length()+"\n");
//        	SimplePoint simplePoint = new SimplePoint(s, PointStatus.CONNECT);
//			SpringUtil.getApplicationContext().publishEvent(new PointRegistPackageEvent(simplePoint));
        }else {
            //保存返回值信息 并 解锁
        	logger.info("接受数据 [" + channel.remoteAddress() + "]: " + s + " 长度 "+s.length()+"\n");
        	String registPackage = getRegistPackageByChannel(channel); 
    		INettyService nettyService = SpringUtil.getBean(INettyService.class);
    		Integer commandRecordId = nettyService.saveReturnMessage(registPackage,s);  
    		logger.info("第三步：保存返回数据到操作记录的commandRecordId："+commandRecordId);
    		if(commandRecordId!=null) {
    			//开启解析事件
    			SpringUtil.getApplicationContext().publishEvent(new CommandCallBackEvent(commandRecordId,registPackage,s));
    		}
        }
    }

    private String getRegistPackageByChannel(Channel channel) {
      Map<Object, Channel> channelMap = NettyServer.channelMap;
      Iterator<Entry<Object, Channel>> iterator = channelMap.entrySet().iterator();
      while(iterator.hasNext()) {
      	Entry<Object, Channel> next = iterator.next(); 
      	if(next.getValue()==channel ) {
      		return next.getKey().toString();
      	}
      }
      return null;
	}

	/**
     * 处理新加的通道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            if (ch == channel) {
                ch.writeAndFlush("[" + channel.remoteAddress() + "] coming");
            }
        }
        group.add(channel);
    }

    /**
     * 处理退出
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            if (ch == channel) {
                ch.writeAndFlush("[" + channel.remoteAddress() + "] leaving");
            }
        }
        group.remove(channel);
//        
//		SimplePoint simplePoint = new SimplePoint(null, PointStatus.OUT_CONNECT);
//		SpringUtil.getApplicationContext().publishEvent(new PointRegistPackageEvent(simplePoint));
        
        //断开连接的时候删除掉该通道
//        Map<Object, Channel> channelMap = NettyServer.channelMap;
//        Iterator<Entry<Object, Channel>> iterator = channelMap.entrySet().iterator();
//        while(iterator.hasNext()) {
//        	Entry<Object, Channel> next = iterator.next(); 
//        	if(next.getValue()==channel ) {
//        		channelMap.remove(next.getKey(),next.getValue());
//        	}
//        }
//        NettyServer.channelMap = channelMap;
    }

    /**
     * 在建立连接时
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean active = channel.isActive();
        if (active) {
            System.out.println("[" + channel.remoteAddress() + "] is online");
        } else {
            System.out.println("[" + channel.remoteAddress() + "] is offline");
        }
    }

    //使用过程中断线重新启动
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        Channel channel = ctx.channel();
        if(!channel.isActive()) {
        	  eventLoop.schedule(new Runnable() {
                  @Override
                  public void run() {
                      new NettyServer().run();
                      logger.info("重启");
                  }
              }, 1L, TimeUnit.SECONDS);
        }
        super.channelInactive(ctx);
    }

    /**
     * 异常捕获
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress() + "]" + e);
        //只要有异常就全部抛出
        throw new Exception(e) ; 
//        System.out.println();
//        ctx.close().sync();
    }

    
}

    
