package com.changlan.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.net.SocketAddress;
import java.util.*;

/**
 * Created by DK on 2017/11/15.
 */
public class StringUtil extends StringUtils {
    static String[]  province  =  new String[]{"京", "沪", "浙", "苏", "粤", "鲁", "晋", "冀", "豫", "川", "渝", "辽", "吉", "黑", "皖", "鄂", "湘", "赣", "闽", "陕", "甘", "宁", "蒙", "津", "贵", "云", "桂", "琼", "青", "新", "藏", "使", "领", "警", "学", "港", "澳"};
    static String[]  letter = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    static String[]  number = new String[]{"0","1","2","3","4","5","6","7","8","9"};

	public static String listToString(Collection<?> coll) {
		return listToString(coll,",");
	}
	
	public static String listToString(Collection<?> coll, String delim) {
		if (CollectionUtils.isEmpty(coll)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		Iterator<?> it = coll.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
            sb.append(delim);
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}


	public static String getLastLetters(String str, int n) {
		return str.substring(str.length()-n);
	}
	
    public static String getSetterName(String fieldName){
        return "set"+toUpperCaseFirstOne(fieldName);
    }
    
    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 判断两个对象是否相等(都为null 不相等)
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean equals(Object o1,Object o2) {
    	return equals(o1,o2,false);
    }


    /**
     * 判断两个对象是否相等
     *
     * @param o1
     * @param o2
     * @param isAllNull 都为null返回该对象
     * @return
     */
    public static boolean equals(Object o1,Object o2,boolean isAllNull) {
    	if(o1 == null && o2==null) {
    		return isAllNull;
    	}
    	if(o1 == null || o2==null) {
    		return false;
    	}
    	return o1.toString().equals(o2.toString());
    }


    public static List<String>  stringToList(String str){
        return stringToList(str,",");
    }

    public static  List<String>  stringToList(String str,String regex){
        if(StringUtil.isBlank(str)){
            return new ArrayList<String>();
        }
        return Arrays.asList(str.split(regex));
    }

    public static  String getRandomCode (){
        String randomCode;
        int rnd = new Random().nextInt(999)+1;
        if(rnd<10) {
            randomCode = "000" + rnd;
        }else if(rnd < 100) {
            randomCode = "00" + rnd;
        }else if(rnd < 1000) {
            randomCode = "0" + rnd;
        }else {
            randomCode = "" + rnd;
        }
        return randomCode;
    }

    /**
     * 任意进制转换 
     * @param oldData 需要转换的数据 仅为数字之间的转换
     * @param oldDecimal  需要转换数据的进制 (注意 此进制只能在 [2,36] 之间)
     * @param nowDecimal  转换后的进制 (注意 此进制只能在 [2,36] 之间)
     * @param nowDataLength 转换后数据的长度 (不足则在前面补0,此字段为null则忽略)
     * @return
     */
    public static String decimalConvert(String oldData,int oldDecimal,int nowDecimal,Integer nowDataLength){
        Long l = Long.parseLong(oldData, oldDecimal);
        String s = Long.toString(l, nowDecimal).toUpperCase();
        if(nowDataLength != null){
            while (s.length()<nowDataLength){
                s="0"+s;
            }
        }
        return  s;
    }
    
    public static String getIpFromAddress(String remoteAddress) {
    	String address = remoteAddress;  
    	int indexOf = address.indexOf(":"); 
    	return address.substring(0,indexOf);
    }
    
    
    //16进制字节数组转字符串。
    public static String bytesToHexString(byte[] bArray) { 
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
    
    
    
    //字符串转16进制
    public static byte[] hexStringToBytes(String hexString) {
    	if (hexString == null || hexString.equals("")) {
    		return null;
    	}
    	hexString = hexString.toUpperCase();
    	int length = hexString.length() / 2;
    	char[] hexChars = hexString.toCharArray();
    	byte[] d = new byte[length];
    	for (int i = 0; i < length; i++) {
    		int pos = i * 2;
    		d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
	
    	}
    	return d;
	}
    
    
    public static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }  
    
    
    /**
     * true表示包含非法字符
     */
    public static Boolean isIllegalStr(String sInput) {
        if (sInput == null || sInput.trim().length() == 0) {
            return false;
        }
        sInput = sInput.trim(); //删除头尾空白符。
        String regex = "·！￥……*（）`~!@#$%^*()_-+{}【】|、：；“‘';[]\\,./<>?《》？，。、/*-+";
        if(regex.indexOf(sInput)>-1) {
//        	System.out.println("未通过字符串检测");
        	return true;
        }
//        	System.out.println("通过字符串检测");
        return false;
    }

    /**
     * true表示包含sql注入
     */
    public static Boolean isIllegalStrContainSql(String sInput) {
        if (sInput == null || sInput.trim().length() == 0) {
            return true;
        }
        sInput = sInput.toUpperCase();
        if (sInput.indexOf("DELETE") >= 0 || sInput.indexOf("ASCII") >= 0 || sInput.indexOf("UPDATE") >= 0 || sInput.indexOf("SELECT") >= 0
                || sInput.indexOf("SUBSTR") >= 0 || sInput.indexOf("COUNT") >= 0 || sInput.indexOf("INSERT") >= 0 
                || sInput.indexOf("DROP") >= 0 || sInput.indexOf("EXECUTE") >= 0 || sInput.indexOf("EXEC") >= 0
                || sInput.indexOf("TRUNCATE") >= 0 || sInput.indexOf("INTO") >= 0 || sInput.indexOf("DECLARE") >= 0 || sInput.indexOf("MASTER") >= 0) {
//            System.out.println("该参数存在SQL注入风险：sInput=" + sInput);
            return true;
        }
//        System.out.println("通过sql检测");
        return false;
    }

    public static void main(String[] args) {
        String s = decimalConvert("0769", 16, 10, null);
        System.out.println(s);
        System.out.println(decimalConvert("0021", 16, 10, null));
        
        //字符串转16进制
        byte[] hexStringToBytes = hexStringToBytes("010324C5C8"); 
        for(int i =0 ;i<hexStringToBytes.length;i++) {
        	System.out.println(hexStringToBytes[i]);
        }
        System.out.println( bytesToHexString(hexStringToBytes));
        
        
        for(int i =0 ;i<hexStringToBytes.length;i++) {
            System.out.print(getRandomCode()+","); 
        }
    
        String str ="1234";
        String replace = str.replace(" ", "");
        System.out.println(replace);
        System.out.println(replace.length());
        System.out.println(replace.substring(0,4)); 
    }
    
    
}
