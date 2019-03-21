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
import io.netty.channel.ChannelId;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
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
        logger.info("接收内容"+s);
        if(StringUtil.isNotEmpty(s)&&s.length()>=4) {
        	 if(s.indexOf("CLKJ")>-1) {
             	//设置注册包
             	setPackageChannel(s,channel);
             	changePointStatus(channel,PointStatus.CONNECT);
             }else {
                 //保存返回值信息 并 解锁
             	String registPackage = getRegistPackageByChannel(channel); 
         		INettyService nettyService = SpringUtil.getBean(INettyService.class);
         		Integer commandRecordId = nettyService.saveReturnMessage(registPackage,s);  
         		logger.info("第三步：[" + channel.remoteAddress() + "]保存返回数据 "+s+ "到操作记录的commandRecordId："+commandRecordId);
         		if(commandRecordId!=null) {
         			//开启解析事件
         			SpringUtil.getApplicationContext().publishEvent(new CommandCallBackEvent(commandRecordId,registPackage,s));
         		}
             	changePointStatus(channel,PointStatus.DATA_CAN_IN);
             }
        }
        context.flush(); //加的部分
    }
    
    private void setPackageChannel(String registPackage, Channel channel) {
    	  Map<Object, Channel> channelMap = NettyServer.channelMap;
    	  channelMap.put(registPackage, channel); 
    	  NettyServer.setChannelMap(channelMap);
    	  logger.info("第一步接受注册包 [" + channel.remoteAddress() + "]:"+ registPackage + " 长度 "+registPackage.length()+"\n");
	}
    

	private void changePointStatus(Channel channel,PointStatus status) { 
		String registPackage= getRegistPackageByChannel(channel);
    	SimplePoint simplePoint = new SimplePoint(registPackage,status);
		SpringUtil.getApplicationContext().publishEvent(new PointRegistPackageEvent(simplePoint));
    }

    private String getRegistPackageByChannel(Channel channel) {
       Map<Object, Channel> channelMap = NettyServer.channelMap;
       Iterator<Entry<Object, Channel>> iterator = channelMap.entrySet().iterator();
       while(iterator.hasNext()) {
        	Entry<Object, Channel> next = iterator.next(); 
      	    if(next.getValue()== channel ) {
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
                ch.writeAndFlush("[" + channel.remoteAddress() + "] coming handlerAdded");
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
        logger.info("[" + channel.remoteAddress() + "] leavinghandlerRemoved");
        //断开连接的时候修改状态
    	changePointStatus(channel,PointStatus.OUT_CONNECT);
        group.remove(channel);
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
        	 logger.info("[" + channel.remoteAddress() + "] is online channelActive");
        } else {
        	 logger.info("[" + channel.remoteAddress() + "] is offline channelActive");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        Channel  channel = ctx.channel();
        String registPackage = getRegistPackageByChannel(channel); 
        changePointStatus(channel,PointStatus.DATA_CAN_NOT_IN);
        logger.info("[" + channel.remoteAddress() + "]"+" 断开 channelInactive &" +registPackage );
    }


	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		Channel channel = ctx.channel();
		if(evt instanceof IdleStateEvent) {
			IdleState state = ((IdleStateEvent) evt).state();
			if(state == IdleState.READER_IDLE ) {
				//关闭通道
				String registPackage = getRegistPackageByChannel(channel); 
	        	changePointStatus(channel,PointStatus.OUT_CONNECT);
	            logger.info("[" + channel.remoteAddress() + "]"+channel+" 心跳监测 未收到回复 &" +registPackage );
			}
		}
		super.userEventTriggered(ctx, evt);
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
//    	System.out.println(e.getMessage());  //远程主机强迫关闭了一个现有的连接。
//    	ctx.close(); //加的部分
    	//只要有异常就全部抛出
        throw new Exception(e); 
    }

    
}	

    
