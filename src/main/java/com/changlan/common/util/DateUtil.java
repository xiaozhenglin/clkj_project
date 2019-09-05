package com.changlan.common.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    
    //区间按月,天,小时,秒来划分
    public static List<Date> cutDate(Integer dateType, String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dBegin = sdf.parse(start);
            Date dEnd = sdf.parse(end);
            return findDates(dateType, dBegin, dEnd);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public static List<Date> findDates(Integer dateType, Date dBegin, Date dEnd) throws Exception {
        List<Date> listDate = new ArrayList();
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (calEnd.after(calBegin)) {
            switch (dateType) {
                case 1:
                    calBegin.add(Calendar.MONTH, 1);
                    break;
                case 2:
                    calBegin.add(Calendar.DAY_OF_YEAR, 1);
                    break;
                case 3:
                    calBegin.add(Calendar.HOUR, 1);
                    break;
                case 4:
                    calBegin.add(Calendar.SECOND, 1);
                    break;
                default:
                	break;
            }
            if (calEnd.after(calBegin)) {
            	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calBegin.getTime())); 
//                listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calBegin.getTime()));
            	listDate.add(calBegin.getTime());
            } else {
//            	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calEnd.getTime())); 
//              listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calEnd.getTime()));
            }
        }
        return listDate;
    }
    
    public static Date yesterDay(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }


    public static void main(String[] args) {
    	String formatSeconds = formatSeconds(46797148); 
    	System.out.println(formatSeconds); 
    	System.out.println(new Date());
    	System.out.println(yesterDay(new Date()));
	}
}
