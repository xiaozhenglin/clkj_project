package com.changlan.common.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by DK on 2017/12/1.
 */
public class DateUtil {
    public  static String  HH_MM = "HH:mm";
    public  static String  YYYY_MM_DD = "yyyy-MM-dd";
    public  static String  YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public  static String  YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public  static String[] adapterFormat = new String[]{YYYY_MM_DD,YYYY_MM_DD_HH_MM,YYYY_MM_DD_HH_MM_SS};
    public  final static float DAY_MILLISECOND=24 * 60 * 60 * 1000;

    public static int getBetweenDays(Date begin,Date end) {
    	return (int)((end.getTime()-begin.getTime())/(24*3600*1000));
    }
    
    public static String formatDate(){
        return formatDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(String pattern){
        return formatDate(new Date(),pattern);
    }

    public static String formatDate(Date date, String pattern){
        return getSimpleDateFormat(pattern).format(date);
    }

    public static  String formatDate(long date, String pattern){
        return formatDate(new Date(date),pattern);
    }

    private static SimpleDateFormat getSimpleDateFormat(String pattern){
        return  new SimpleDateFormat(pattern);
    }
    
    public static Date parseDate(String date , String pattern) {
    	try {
			return getSimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static Date parseDate(String date) {
    	try {
			return getSimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }

    /** 
     * 秒转化为天小时分秒字符串 
     * 
     * @param seconds 
     * @return String 
     */  
    public static String formatSeconds(long seconds) {  
        String timeStr = seconds + "秒";  
        if (seconds > 60) {  
            long second = seconds % 60;   //剩余秒数
            long min = seconds / 60;   //总分钟数
            timeStr = min + "分" + second + "秒";  
            if (min > 60) {  
                min = (seconds / 60) % 60;  
                long hour = (seconds / 60) / 60;  
                timeStr = hour + "小时" + min + "分" + second + "秒";  
                if (hour > 24) {  
                    hour = ((seconds / 60) / 60) % 24;  
                    long day = (((seconds / 60) / 60) / 24);  
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";  
                }  
            }  
        }  
        return timeStr;  
    }  

    public static void main(String[] args) {
    	String formatSeconds = formatSeconds(46797148); 
    	System.out.println(formatSeconds); 
	}
}
