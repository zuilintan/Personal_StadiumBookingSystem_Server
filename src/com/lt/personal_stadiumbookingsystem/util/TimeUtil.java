package com.lt.personal_stadiumbookingsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @作者: LinTan
 * @日期: 2019/5/1 20:57
 * @版本: 1.0
 * @描述: //日期时间的工具类。
 * 1.0: Initial Commit
 */

public class TimeUtil {
    public static final String FORMAT_DATE = "yyyy-MM-dd";//mode = 1
    public static final String FORMAT_TIME = "HH:mm:ss";//mode = 2
    public static final String FORMAT_TIME_CLEAN = "HH:mm";//mode = 3
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";//mode = 4
    public static final String FORMAT_DATE_TIME_CLEAN = "yyyy-MM-dd HH:mm";//mode = 5
    private static Calendar sCalendar = Calendar.getInstance();

    private TimeUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 目标时间是否过期
     */
    public static boolean isExpire(String targetTime, String format) {
        boolean result;
        Date target = string2Date(targetTime, format);
        Date current = new Date();
        if (target.getTime() == current.getTime()) {
            result = false;
        } else {
            Calendar targetCalendar = Calendar.getInstance();
            targetCalendar.setTime(target);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(current);
            result = currentCalendar.after(targetCalendar);
        }
        return result;
    }

    /**
     * 目标时间是否有效(处于指定时段内)
     */
    public static boolean isValid(String targetTime, String startTime, String endTime, String format) {
        boolean result;
        Date target = string2Date(targetTime, format);
        Date start = string2Date(startTime, format);
        Date end = string2Date(endTime, format);
        if (target.getTime() == start.getTime() || target.getTime() == end.getTime()) {
            result = false;
        } else {
            Calendar targetCalendar = Calendar.getInstance();
            targetCalendar.setTime(target);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(start);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(end);
            result = targetCalendar.after(startCalendar) && targetCalendar.before(endCalendar);
        }
        return result;
    }

    /**
     * 获取日期，并设置偏移量
     * eg: offsetDay为1，即得到明天的日期，offsetDay为-1，即得到昨天的日期
     */
    public static String getDate(int offsetDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, offsetDay);
        return date2String(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 获取日期
     */
    public static String getDate() {
        return date2String(new Date(), FORMAT_DATE);
    }

    public static String getDate(String format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取时间
     */
    public static String getTime() {
        return date2String(new Date(), FORMAT_TIME);
    }

    public static String getTime(String format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取日期和时间
     */
    public static String getDateTime() {
        return date2String(new Date(), FORMAT_DATE_TIME);
    }

    public static String getDateTime(String format) {
        return date2String(new Date(), format);
    }

    /**
     * String转Date
     */
    public static Date string2Date(String string, String format) {
        Date result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Date转String
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    public static String getDayOfWeek(int offsetDay) {
        int index = getDayOfWeekNumber() + offsetDay;
        if (index < 0) {
            index = 7 - index * -1 % 7;
        }//规范索引的范围
        String[] strings = {"日", "一", "二", "三", "四", "五", "六"};
        return "星期" + strings[index];
    }//获取星期

    public static int getYearNumber() {
        return sCalendar.get(Calendar.YEAR);
    }//获取当前的年数

    public static int getMonthNumber() {
        int indexTemp = sCalendar.get(Calendar.MONTH);
        return indexTemp + 1;
    }//获取今年的月数

    public static int getDayOfMonthNumber() {
        return sCalendar.get(Calendar.DAY_OF_MONTH);
    }//获取本月的天数

    public static int getDayOfWeekNumber() {
        int indexTemp = sCalendar.get(Calendar.DAY_OF_WEEK);//注意，sCalendar.get返回的是数组中元素的索引值，而不是元素值
        return indexTemp - 1;
    }//获取本周的天数

    public static int getHourNumber() {
        return sCalendar.get(Calendar.HOUR_OF_DAY);
    }//获取今天的小时数

    public static int getMinuteNumber() {
        return sCalendar.get(Calendar.MINUTE);
    }//获取该小时的分数

    public static int getSecondNumber() {
        return sCalendar.get(Calendar.SECOND);
    }//获取该分钟的秒数
}
