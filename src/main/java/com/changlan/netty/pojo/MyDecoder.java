package com.changlan.netty.pojo;

import java.util.List;

import com.changlan.common.util.StringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MyDecoder extends ByteToMessageDecoder {
	
	
   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
	   //创建字节数组,buffer.readableBytes可读字节长度
       byte[] b = new byte[buffer.readableBytes()];
       //复制内容到字节数组b
       buffer.readBytes(b);
       //字节数组转字符串
       String str = new String(b);
       System.out.println("接收内容"+str);
//       System.out.println(str);
       //进入的数据解码后丢到接受消息方法中去
       if(StringUtil.isNotEmpty(str)&& str.length() >4) {
    	   if(str.indexOf("CLKJ")>-1) {
               out.add(str);
           }
       }else {
    	   out.add(bytesToHexString(b));
       }
   }
   
   

   public String bytesToHexString(byte[] bArray) {
       StringBuffer sb = new StringBuffer(bArray.length);
       String sTemp;
       for (int i = 0; i < bArray.length; i++) {
           sTemp = Integer.toHexString(0xFF & bArray[i]);
           if (sTemp.length() < 2)
               sb.append(0);
           sb.append(sTemp.toUpperCase());
       }
       return sb.toString();
   }

   public static String toHexString1(byte[] b) {
       StringBuffer buffer = new StringBuffer();
       for (int i = 0; i < b.length; ++i) {
           buffer.append(toHexString1(b[i]));
       }
       return buffer.toString();
   }

   public static String toHexString1(byte b) {
       String s = Integer.toHexString(b & 0xFF);
       if (s.length() == 1) {
           return "0" + s;
       } else {
           return s;
       }
   }
}

