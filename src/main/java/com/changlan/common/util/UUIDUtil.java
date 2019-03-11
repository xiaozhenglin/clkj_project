package com.changlan.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDUtil {
    /**
     * 获取一个UUID值
     * @return UUID值[String]
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 获取多个UUID值
     * @param number 所需个数
     * @return UUID集合
     */
    public static List<String> getUUID(Integer number){
        List<String> list = new ArrayList<>();
        while (0 <= (number--)){
            list.add(getUUID());
        }
        return list;
    }
    public static void main(String[] args) {
    	String uuid = getUUID();
    	System.out.println(uuid+ ":" + uuid.length());
	}
}

