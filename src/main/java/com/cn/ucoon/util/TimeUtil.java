package com.cn.ucoon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TimeUtil {


	
	/** 
     * @param oldTime 较小的时间 
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比) 
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天. 
     * @throws ParseException 转换异常 
     */  
	public static int isYeaterday(Date oldTime,Date newTime) throws ParseException{  
        if(newTime==null){  
            newTime=new Date();  
        }  
               //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String todayStr = format.format(newTime);  
        Date today = format.parse(todayStr);  
        //昨天 86400000=24*60*60*1000 一天  
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {  
            return 0;  
        }  
        else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天  
            return -1;  
        }  
        else{ //至少是前天  
            return 1;  
        }  
          
    }  
	
	
	public static boolean isToday(Date date) { 
	    Calendar c1 = Calendar.getInstance();              
	    c1.setTime(date);                                 
	    int year1 = c1.get(Calendar.YEAR);
	        int month1 = c1.get(Calendar.MONTH)+1;
	        int day1 = c1.get(Calendar.DAY_OF_MONTH);     
	        Calendar c2 = Calendar.getInstance();    
	        c2.setTime(new Date());      
	        int year2 = c2.get(Calendar.YEAR);
	        int month2 = c2.get(Calendar.MONTH)+1;
	        int day2 = c2.get(Calendar.DAY_OF_MONTH);   
	        if(year1 == year2 && month1 == month2 && day1 == day2){
	        return true;
	        }
	    return false;                               
	}
	
	
	/** 
    * 时间戳转换成日期格式字符串 
    * @param seconds 精确到秒的字符串 
    * @param formatStr 
    * @return 
    */  
   public static String timeStamp2Date(String seconds,String format) {  
       if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
           return "";  
       }  
       if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
       SimpleDateFormat sdf = new SimpleDateFormat(format);  
       return sdf.format(new Date(Long.valueOf(seconds+"000")));  
   }  
   /** 
    * 日期格式字符串转换成时间戳 
    * @param date 字符串日期 
    * @param format 如：yyyy-MM-dd HH:mm:ss 
    * @return 
    */  
   public static String date2TimeStamp(String date_str,String format){  
       try {  
           SimpleDateFormat sdf = new SimpleDateFormat(format);  
           return String.valueOf(sdf.parse(date_str).getTime()/1000);  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return "";  
   }  
     
   /** 
    * 取得当前时间戳（精确到秒） 
    * @return 
    */  
   public static int timeStamp(){  
       long time = System.currentTimeMillis();  
       int t = (int) (time/1000);  
       return t;  
   }  
     
   
  
   //  输出结果：  
   //  timeStamp=1417792627  
   //  date=2014-12-05 23:17:07  
   //  1417792627  
   @Test
   public void test3() {  
       int timeStamp = timeStamp();  
       System.out.println("timeStamp="+timeStamp);  
         
       String date = timeStamp2Date(String.valueOf(timeStamp), "yyyy-MM-dd HH:mm:ss");  
       System.out.println("date="+date);  
         
       String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");  
       System.out.println(timeStamp2);  
   }  
}
