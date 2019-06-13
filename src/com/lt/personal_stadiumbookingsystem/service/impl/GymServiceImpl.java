package com.lt.personal_stadiumbookingsystem.service.impl;

import com.lt.personal_stadiumbookingsystem.dao.IGymDao;
import com.lt.personal_stadiumbookingsystem.dao.IOrderDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.GymDaoImpl;
import com.lt.personal_stadiumbookingsystem.dao.impl.OrderDaoImpl;
import com.lt.personal_stadiumbookingsystem.dao.impl.TimeDaoImpl;
import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.entity.Time;
import com.lt.personal_stadiumbookingsystem.service.IGymService;
import com.lt.personal_stadiumbookingsystem.util.C3P0Util;
import com.lt.personal_stadiumbookingsystem.util.TimeUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/28 17:01
 * @版本: 1.0
 * @描述: //场馆Service实现类
 * 1.0: Initial Commit
 */

public class GymServiceImpl implements IGymService {

    @Override
    public boolean vindicate(Gym gym) {
        IGymDao dao = new GymDaoImpl();
        boolean success;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            String gymName = gym.getGym_name();
            int i = dao.change("gym_managestate", 0, gymName);
            if (i > 0) {
                String today = TimeUtil.getDate(0);
                String tomorrow = TimeUtil.getDate(1);
                String dayAfterDay = TimeUtil.getDate(2);
                int a1 = 0;
                int a2 = 0;
                IOrderDao orderDao = new OrderDaoImpl();
                List<Time> timeList = TimeDaoImpl.initTime();
                for (Time time : timeList) {
                    String timeStart = time.getTime_start();
                    boolean isExpire = TimeUtil.isExpire(today + " " + timeStart, TimeUtil.FORMAT_DATE_TIME);
                    if (!isExpire) {
                        a1 = orderDao.changeList(today, timeStart, 0, gymName);
                        a2 = orderDao.changeList(today, timeStart, 1, gymName);
                    }
                }
                int b1 = orderDao.changeList(tomorrow, "%", 0, gymName);
                int b2 = orderDao.changeList(tomorrow, "%", 1, gymName);
                int c1 = orderDao.changeList(dayAfterDay, "%", 0, gymName);
                int c2 = orderDao.changeList(dayAfterDay, "%", 1, gymName);
                System.out.println("订单表修改结果: ");
                System.out.println("今天的订单: " + (a1 + a2));
                System.out.println("明天的订单: " + (b1 + b2));
                System.out.println("后天的订单: " + (c1 + c2));
                success = true;
                System.out.println("场馆维护成功");
            } else {
                success = false;
                System.out.println("场馆维护失败");
                C3P0Util.rollbackTransaction();
                System.out.println("回滚事务成功");
            }
        } catch (SQLException e) {
            System.out.println("场馆维护失败，" + e.getMessage());
            success = false;
            C3P0Util.rollbackTransaction();
            System.out.println("回滚事务成功");
        } finally {
            C3P0Util.commitTransaction();
            System.out.println("提交事务成功");
        }
        return success;
    }

    @Override
    public boolean operate(Gym gym) {
        IGymDao dao = new GymDaoImpl();
        boolean success;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            int i = dao.change("gym_managestate", 1, gym.getGym_name());
            if (i > 0) {
                success = true;
                System.out.println("场馆营业成功");
            } else {
                success = false;
                System.out.println("场馆营业失败");
                C3P0Util.rollbackTransaction();
                System.out.println("回滚事务成功");
            }
        } catch (SQLException e) {
            System.out.println("场馆营业失败" + e.getMessage());
            success = false;
            C3P0Util.rollbackTransaction();
            System.out.println("回滚事务成功");
        } finally {
            C3P0Util.commitTransaction();
            System.out.println("提交事务成功");
        }
        return success;
    }

    @Override
    public List<Gym> showAll() {
        IGymDao dao = new GymDaoImpl();
        List<Gym> result = null;
        try {
            result = dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
