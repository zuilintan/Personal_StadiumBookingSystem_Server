package com.lt.personal_stadiumbookingsystem.test;

import com.lt.personal_stadiumbookingsystem.dao.IAccountDao;
import com.lt.personal_stadiumbookingsystem.dao.IGymDao;
import com.lt.personal_stadiumbookingsystem.dao.IOrderDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.*;
import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.entity.Order;
import com.lt.personal_stadiumbookingsystem.entity.Site;
import com.lt.personal_stadiumbookingsystem.util.C3P0Util;
import com.lt.personal_stadiumbookingsystem.util.TimeUtil;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/5/1 19:27
 * @版本: 1.0
 * @描述: //单元测试类
 * 1.0: Initial Commit
 */

public class TestDao {

    public static void main(String[] args) throws Exception {
        //testAccount1();
        //testAccount2();
        //testAccount3();
        //testGym();
        //testSite();
        //testTime();
        //testOrder1();
        //testOrder2();
        //testOrder3();
        //testOrder4();
        //testOrder5();
        //System.out.println(UUIDUtil.getOrderNumber());
    }

    private static void testAccount1() throws SQLException {
        System.out.println("testAccount1:");
        IAccountDao dao = new AccountDaoImpl();
        Account obj = dao.find("zuijingtian", "user");
        System.out.println(obj);
        System.out.println(GsonUtil.objectToJson(obj));
        System.out.println();
    }

    private static void testAccount2() throws SQLException {
        System.out.println("testAccount2:");
        IAccountDao dao = new AccountDaoImpl();
        dao.change("account_passwd", "00000000", "zuiyufei");
        System.out.println();
    }

    private static void testAccount3() throws SQLException {
        System.out.println("testAccount3:");
        IAccountDao dao = new AccountDaoImpl();
        int i1 = dao.change("account_coin", "account_coin - " + "1", "zuhao");
        int i2 = dao.change("account_coin", "account_coin - 10", "zuhao");
        System.out.println(i1);
        System.out.println(i2);
    }

    private static void testGym() throws SQLException {
        System.out.println("testGym:");
        IGymDao dao = new GymDaoImpl();
        List<Gym> list = dao.findAll();
        System.out.println(list);
        System.out.println(GsonUtil.objectToJson(list));
        System.out.println();
    }

    private static void testSite() throws SQLException {
        System.out.println("testSite:");
        SiteDaoImpl dao = new SiteDaoImpl();
        List<Site> list = dao.findList("篮球馆");
        System.out.println(list);
        System.out.println(GsonUtil.objectToJson(list));
        System.out.println();
    }

    private static void testTime() throws SQLException {
        System.out.println("testTime:");
        TimeDaoImpl dao = new TimeDaoImpl();
        List<Map<String, Object>> list = dao.findList("3", "台球馆");
        System.out.println(list);
        System.out.println(GsonUtil.objectToJson(list));
        System.out.println();
    }

    private static void testOrder1() throws SQLException {
        System.out.println("testOrder1:");
        OrderDaoImpl dao = new OrderDaoImpl();
        List<Order> list = dao.findList("zuitangling");
        System.out.println(list);
        System.out.println(GsonUtil.objectToJson(list));
        System.out.println();
    }

    private static void testOrder2() throws SQLException {
        System.out.println("testOrder2:");
        OrderDaoImpl dao = new OrderDaoImpl();
        List<Order> list = dao.findAll();
        System.out.println(list);
        System.out.println(GsonUtil.objectToJson(list));
        System.out.println();
    }

    private static void testOrder3() throws SQLException {
        System.out.println("testOrder3:");
        OrderDaoImpl dao = new OrderDaoImpl();
        List<Order> list = dao.findList("yefan", 2, 20);
        System.out.println(list);
        System.out.println(GsonUtil.objectToJson(list));
        System.out.println();
    }

    private static void testOrder4() throws SQLException {
        System.out.println("testOrder4:");
        OrderDaoImpl dao = new OrderDaoImpl();
        List<Order> list = dao.findAll();
        System.out.println(list);
        System.out.println(GsonUtil.objectToJson(list));
        System.out.println();
    }

    private static void testOrder5() {
        try {
            //要执行的任务逻辑写在这里
            C3P0Util.beginTransaction();
            System.out.println("事务开启成功");
            IOrderDao orderDao = new OrderDaoImpl();
            String date = TimeUtil.getDate();
            String time = TimeUtil.getTime(TimeUtil.FORMAT_TIME_CLEAN);
            System.out.println("当前时间: " + date + " " + time);

            System.out.println("订单任务1: 每天时段开始后，将当前时段下的所有待付款的订单，其订单状态改为已取消(即order_state置为3)");
            int i1 = orderDao.changeList("order_state", 3, date,  "%", 0);
            System.out.println("订单任务1修改的数据量: " + i1);

            System.out.println("订单任务2: 每天时段开始后，将当前时段下的所有已付款的订单，其订单状态改为已完成(即order_state置为2)");
            int i2 = orderDao.changeList("order_state", 2, date,  "%", 1);
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
