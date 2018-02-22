package com.cos.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 遍历两个日期之间天数的算法
 */
public class DateTimeUtil extends DateUtils{

    // 默认日期格式
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
    // 默认时间格式
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
    
    public static long MS_IN_MINUTE = 1000L * 60;
    public static long MS_IN_HOUR = MS_IN_MINUTE * 60;
    public static long MS_IN_DAY = MS_IN_HOUR * 24;
    
    public static Date min(Date... dates) {
        List<Date> dls = Arrays.asList(dates);
        sortDate(dls, "");
        if(CollectionUtils.isNotEmpty(dls)){
            return dls.iterator().next();
        }
        return null;
    }
    
    public static Date max(Date... dates) {
        List<Date> dls = Arrays.asList(dates);
        sortDate(dls, "desc");
        if(CollectionUtils.isNotEmpty(dls)){
            return dls.iterator().next();
        }
        return null;
    }
    
    public static List<Date> sortDate(List<Date> dates, final String descOrAsc) {
        Collections.sort(dates, new Comparator<Date>(){
            int t1 = 1;
            int t0 = 0;
            public int compare(Date o1, Date o2) {
                if(StringUtils.isNotBlank(descOrAsc) && StringUtils.equalsIgnoreCase(descOrAsc, "desc")){
                    t1 = 0;
                    t0 = 1;
                }
                if(null != o1 && null != o2){
                    if(o1.after(o2)){
                        return t1;
                    }
                }else if(null == o1){
                    return 1;
                }else{
                    return 0;
                }
                return t0;
            }
        });
        return dates;
    }
    
    /**
     * 两个日期之间相差的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int startToEndCount(Date startDate, Date endDate) {
        String shortDataFormat = "yyyy-MM-dd";
        try
        {
            startDate = DateTimeUtil.parseDate(DateFormatUtils.format(startDate, shortDataFormat), shortDataFormat);
            endDate = DateTimeUtil.parseDate(DateFormatUtils.format(endDate, shortDataFormat), shortDataFormat);
            return (int) ((endDate.getTime() - startDate.getTime()) / MS_IN_DAY);
        }
        catch (ParseException e)
        {
            return -1;
        }
    }
 
 
	public static Date GetCurrentDate(){
		 java.util.Calendar c=java.util.Calendar.getInstance();  
		 return c.getTime();
	}
	public static String getCurrentDateString(){
		 java.util.Calendar c=java.util.Calendar.getInstance();  
		return formatDate(c.getTime(),"yyyy-MM-dd HH:mm:ss");
	}
	/*"yyyy-MM-dd hh:mm:ss"*/
    public static String formatDate(Date date,String format){
        DateFormat df = new SimpleDateFormat(format);
        if(null == date || StringUtils.isEmpty(format)){
            return "";
        }
        return df.format(date);
    }
  
    public static Date formatStringDate(String dateStr,String format){
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(dateStr);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e.toString());
        }
       
    }
    
    public static Date parseDate(String date,String format) throws ParseException{
    	Date d = org.apache.commons.lang3.time.DateUtils.parseDate(date, format);
    	return d;
    }
    public static Date addDayToDate(Date date,int day){
        if(null == date){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,day);
        return calendar.getTime();
    }

    public static String addDayFormatDate(Date date,int day,String format){
        DateFormat df = new SimpleDateFormat(format);
        if(null == date || StringUtils.isEmpty(format)){
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        return df.format(calendar.getTime());
    }

    // 获得当前日期与本周日相差的天数
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    //获得计算后的日期
    public static String getMathday(int plus, String format) {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus+plus);
        Date date = currentDate.getTime();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    //本周一
    public static String getMonDay(){
        return getMathday(0, DATE_DEFAULT_FORMAT);
    }

    //本周日
    public static String getSunDay(){
        return getMathday(6, DATE_DEFAULT_FORMAT);
    }

    //上周一
    public static String getLastMonDay(){
        return getMathday(-7, DATE_DEFAULT_FORMAT);
    }

    //上周日
    public static String getLastSunDay(){
        return getMathday(-1, DATE_DEFAULT_FORMAT);
    }

    //获取当月第一天
    public static String getFirstDayOfMonth(){
        SimpleDateFormat sdf=new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE,1);//设为当前月的1号
        return sdf.format(lastDate.getTime());
    }

    //获取当月最后一天
    public static String getLastDayOfMonth(){
        SimpleDateFormat sdf=new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE,1);//设为当前月的1号
        lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号
        lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
        return sdf.format(lastDate.getTime());
    }

    //获取上月第一天
    public static String getFirstDayOfLastMonth(){
        SimpleDateFormat sdf=new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE,1);//设为当前月的1号
        lastDate.add(Calendar.MONTH,-1);//减一个月，变为上月的1号
        return sdf.format(lastDate.getTime());
    }

    //获取上月最后一天
    public static String getLastDayOfLastMonth(){
        SimpleDateFormat sdf=new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE,1);//设为当前月的1号
        lastDate.add(Calendar.DATE,-1);//减去一天，变为上月最后一天
        return sdf.format(lastDate.getTime());
    }
    /** 
     * 获取某个日期距离当前日多少天
     *  
     * @param time 
     * @param format 
     * @return 
     */  
    public static String getDayNumFromCurrentDay(Date date) {
    	long  s1=date.getTime();//将时间转为毫秒
    	long s2=System.currentTimeMillis();//得到当前的毫秒
    	long  hour=(s2-s1)/1000/60/60;
    	Long d=hour/24;
    	long h=hour % 24;
    	if((s2-s1)/1000<60){
    		return ((s2-s1<1000L?1000L:s2-s1)/1000)+"秒前";
    	}
    	if(hour<1){
    		return (s2-s1)/1000/60+"分钟前";
    	}
    	if(hour>=1 && hour<24){
    		return hour+"小时前";
    	}
    	if(d>=1 && d<30){
    		return d+"天前";
    	}
    	if(d>30){
    		return "1月前";
    	}
		return d+"天"+h+"小时";
    }  
    /** 
     * 将长整型数字转换为日期
     *  
     * @param time 
     * @param format 
     * @return 
     */  
    public static Date convertLongToDate(Long timeValue,String format) {  
    	 Date date= new Date(timeValue);
    	 DateFormat df = new SimpleDateFormat(format);
         try {
             String d= df.format(date);
             date=df.parse(d);
             return date;
         } catch (ParseException e) {
             throw new RuntimeException(e.toString());
         }
       }     
   
   
      
}