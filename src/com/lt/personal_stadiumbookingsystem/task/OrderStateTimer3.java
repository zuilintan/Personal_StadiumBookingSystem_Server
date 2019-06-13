package com.lt.personal_stadiumbookingsystem.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * @作者: LinTan
 * @日期: 2019/5/14 14:11
 * @版本: 1.0
 * @描述: //定时任务管理器
 * 1.0: Initial Commit
 */

public class OrderStateTimer3 {

    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;//时间间隔:24h

    public OrderStateTimer3() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 0);

        Date date = calendar.getTime();//获取第一次执行定时任务的时间

        if (new Date().after(date)) {
            date = addDay(date, 1);
        }//如果所定时的时间点已过，则在第二天时间点开始执行

        Timer timer = new Timer();
        OrderStateTask task = new OrderStateTask();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行
        timer.schedule(task, date, PERIOD_DAY);
    }

    private Date addDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }//增加或减少天数
}
