package com.lt.personal_stadiumbookingsystem.web.listener;

import com.lt.personal_stadiumbookingsystem.task.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @作者: LinTan
 * @日期: 2019/5/14 13:58
 * @版本: 1.0
 * @描述: //定时任务监听器
 * 1.0: Initial Commit
 */

@WebListener()
public class TimerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new AccountCoinTimer();//硬币重置定时任务
        new OrderStateTimer1();//08:20定时任务
        new OrderStateTimer2();//10:05定时任务
        new OrderStateTimer3();//11:50定时任务
        new OrderStateTimer4();//13:35定时任务
        new OrderStateTimer5();//15:20定时任务
        new OrderStateTimer6();//17:05定时任务
        new OrderStateTimer7();//18:50定时任务
        System.out.println("定时任务监听器初始化成功");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
