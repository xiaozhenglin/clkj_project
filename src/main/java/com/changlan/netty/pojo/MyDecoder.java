package com.changlan.netty.pojo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changlan.common.pojo.MyDefineException;
import com.changlan.common.util.CRC16M;
import com.changlan.common.util.StringUtil;
import com.changlan.point.pojo.PoinErrorType;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MyDecoder extends ByteToMessageDecoder {
   
   private Logger logger = LoggerFactory.getLogger(this.getClass());
	
   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
	   //创建字节数组,buffer.readableBytes可读字节长度
       byte[] b = new byte[buffer.readableBytes()];
       //复制内容到字节数组b
       buffer.readBytes(b);
       //字节数组转字符串
       String str = new String(b);
       logger.info("MyDecoder类接收内容"+str);

       if(StringUtil.isNotEmpty(str)) {
    	   if(str.indexOf("CLKJ")>-1) {
    		   //注册包
               out.add(str);
           } else {
               //进入的数据解码后丢到接受消息方法中去
        	   String bytesToHexString = bytesToHexString(b); 
        	   byte[] sbuf = CRC16M.getSendBuf(bytesToHexString.substring(0,bytesToHexString.length()-4));
        	   boolean equalsIgnoreCase = bytesToHexString.equalsIgnoreCase(CRC16M.getBufHexStr(sbuf).trim()); 
        	   if(equalsIgnoreCase){
        		   out.add(bytesToHexString);
        	   }else {
        		   throw new MyDefineException(PoinErrorType.RECEIVE_CRC_ERROR); 
        	   }
           }
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

//   public static String toHexString1(byte[] b) {
//       StringBuffer buffer = new StringBuffer();
//       for (int i = 0; i < b.length; ++i) {
//           buffer.append(toHexString1(b[i]));
//       }
//       return buffer.toString();
//   }
//
//   public static String toHexString1(byte b) {
//       String s = Integer.toHexString(b & 0xFF);
//       if (s.length() == 1) {
//           return "0" + s;
//       } else {
//           return s;
//       }
//   }
}

