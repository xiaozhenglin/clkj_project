package com.changlan.common.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.changlan.point.action.VarController;

/**
 * Base64加密工具类
 *
 */
public class Base64Util {

    /**
     * @param data 要加密的字符数组
     * @return String 加密后的16进制字符串
     */
    public static String encode_JDK18(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * @param data 要解密的字符串
     * @return  解密后的字符串
     * @throws IOException
     */
    public static byte[]  decode_JDK18(String data) throws IOException {
        return Base64.getDecoder().decode(data);
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        String encryptMessage = "gkAQAAUCIQsGDQEZALIFoAoOQg==";
//        byte[] decode_JDK18 = decode_JDK18(encryptMessage);
//        for(int i =0;i<decode_JDK18.length;i++) {
//        	System.out.print(" "+ decode_JDK18[i] + " " );  
//        }
//        System.out.println();
//        String bytesToHexString = StringUtil.bytesToHexString(decode_JDK18);
//        System.out.println(bytesToHexString); 
    	
//    	  List<String> sortValue = Arrays.asList("-NAME|RECORD_TIME".split("\\|"));
//    	  for(String str : sortValue) {
//    		  System.out.println(str);
//    	  }
    }
}

