package com.lt.personal_stadiumbookingsystem.task;

import com.lt.personal_stadiumbookingsystem.dao.IOrderDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.OrderDaoImpl;
import com.lt.personal_stadiumbookingsystem.util.C3P0Util;
import com.lt.personal_stadiumbookingsystem.util.TimeUtil;

import java.util.TimerTask;

/**
 * @作者: LinTan
 * @日期: 2019/5/14 14:13
 * @版本: 1.0
 * @描述: //定时任务
 * 1.0: Initial Commit
 */

public class OrderStateTask extends TimerTask {

    @Override
    public void run() {
        try {
            C3P0Util.beginTransaction();
            System.out.println("事务开启成功");
            IOrderDao orderDao = new OrderDaoImpl();
            String date = TimeUtil.getDate();
            String time = TimeUtil.getTime(TimeUtil.FORMAT_TIME_CLEAN);
            System.out.println("当前时间: " + date + " " + time);

            System.out.println("订单任务1: 将过期的所有待付款的订单，其订单状态改为已取消(即order_state置为3)");
            int i1 = orderDao.changeList("order_state", 3, date, time + "%", 0);
            System.out.println("订单任务1修改的数据量: " + i1);

            System.out.println("订单任务2: 将过期的所有已付款的订单，其订单状态改为已完成(即order_state置为2)");
            int i2 = orderDao.changeList("order_state", 2, date, time + "%", 1);
            System.out.println("订单任务2修改的数据量: " + i2);

            System.out.println("订单的定时任务执行成功");
        } catch (Exception e) {
            System.out.println("订单的定时任务执行失败，" + e.getMessage());
            C3P0Util.rollbackTransaction();
            System.out.println("事务回滚成功");
        } finally {
            C3P0Util.commitTransaction();
            System.out.println("事务提交成功");
        }
    }
}
