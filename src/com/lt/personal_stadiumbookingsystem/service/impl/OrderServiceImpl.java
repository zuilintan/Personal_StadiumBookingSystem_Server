package com.lt.personal_stadiumbookingsystem.service.impl;

import com.lt.personal_stadiumbookingsystem.dao.IAccountDao;
import com.lt.personal_stadiumbookingsystem.dao.IGymDao;
import com.lt.personal_stadiumbookingsystem.dao.IOrderDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.AccountDaoImpl;
import com.lt.personal_stadiumbookingsystem.dao.impl.GymDaoImpl;
import com.lt.personal_stadiumbookingsystem.dao.impl.OrderDaoImpl;
import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.entity.Order;
import com.lt.personal_stadiumbookingsystem.service.IOrderService;
import com.lt.personal_stadiumbookingsystem.util.C3P0Util;
import com.lt.personal_stadiumbookingsystem.util.TimeUtil;
import com.lt.personal_stadiumbookingsystem.util.UUIDUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 21:36
 * @版本: 1.0
 * @描述: //订单Service实现类
 * 1.0: Initial Commit
 */

public class OrderServiceImpl implements IOrderService {

    @Override
    public Order book(Order order) {
        IOrderDao dao = new OrderDaoImpl();
        Order result = null;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            IGymDao gymDao = new GymDaoImpl();
            Gym gym = gymDao.find(order.getGym_name());
            if (gym.getGym_managestate() == 0) {
                System.out.println("下单失败，场馆维护中");
            } else {
                int i = dao.find(order.getOrder_date(),
                                 order.getOrder_timestart(),
                                 order.getOrder_timeend(),
                                 order.getSite_number(),
                                 order.getGym_name(),
                                 1);
                if (i > 0) {
                    System.out.println("下单失败，存在相同条件下已付款的订单");
                } else {
                    String orderNumber = UUIDUtil.getOrderNumber();
                    int i1 = dao.add(orderNumber,
                                     order.getOrder_date(), order.getOrder_timestart(),
                                     order.getOrder_timeend(), order.getAccount_name(),
                                     order.getSite_number(), order.getGym_name());
                    if (i1 > 0) {
                        result = dao.find(orderNumber);
                        System.out.println("下单成功");
                    } else {
                        System.out.println("下单失败");
                        C3P0Util.rollbackTransaction();
                        System.out.println("回滚事务成功");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("下单失败，" + e.getMessage());
            C3P0Util.rollbackTransaction();
            System.out.println("回滚事务成功");
        } finally {
            C3P0Util.commitTransaction();
            System.out.println("提交事务成功");
        }
        return result;
    }

    @Override
    public boolean pay(Order order) {
        IOrderDao dao = new OrderDaoImpl();
        boolean success = false;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            order = dao.find(order.getOrder_number());
            String dateTime = order.getOrder_date() + " " + order.getOrder_timestart();
            boolean isExpire = TimeUtil.isExpire(dateTime, TimeUtil.FORMAT_DATE_TIME);
            if (isExpire) {
                System.out.println("支付失败，订单已过期");
            } else {
                IGymDao gymDao = new GymDaoImpl();
                Gym gym = gymDao.find(order.getGym_name());
                if (gym.getGym_managestate() == 0) {
                    System.out.println("支付失败，场馆维护中");
                } else {
                    int i = dao.find(order.getOrder_date(),
                                     order.getOrder_timestart(),
                                     order.getOrder_timeend(),
                                     order.getSite_number(),
                                     order.getGym_name(),
                                     1);
                    if (i > 0) {
                        System.out.println("支付失败，存在相同条件下已付款的订单");
                    } else {
                        IAccountDao accountDao = new AccountDaoImpl();
                        int result1 = accountDao.change("account_coin",
                                                        "account_coin",
                                                        -order.getGym_price(),
                                                        order.getAccount_name());
                        System.out.println("用户表修改结果: " + result1);
                        int result2 = dao.change("order_state", 1, order.getOrder_number());
                        System.out.println("订单表修改结果: " + result2);
                        if (result1 > 0 && result2 > 0) {
                            success = true;
                            System.out.println("支付成功");
                        } else {
                            success = false;
                            System.out.println("支付失败");
                            C3P0Util.rollbackTransaction();
                            System.out.println("回滚事务成功");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("支付失败，" + e.getMessage());
            C3P0Util.rollbackTransaction();
            success = false;
            System.out.println("回滚事务成功");
        } finally {
            C3P0Util.commitTransaction();
            System.out.println("提交事务成功");
        }
        return success;
    }

    @Override
    public boolean cancel(Order order) {
        IOrderDao dao = new OrderDaoImpl();
        boolean success = false;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            order = dao.find(order.getOrder_number());
            String dateTime = order.getOrder_date() + " " + order.getOrder_timestart();
            boolean isExpire = TimeUtil.isExpire(dateTime, TimeUtil.FORMAT_DATE_TIME);
            if (isExpire) {
                System.out.println("取消失败，订单已过期");
            } else {
                int result1 = -1;
                if (order.getOrder_state() == 1) {
                    IAccountDao accountDao = new AccountDaoImpl();
                    result1 = accountDao.change("account_coin",
                                                "account_coin",
                                                +order.getGym_price(),
                                                order.getAccount_name());
                    System.out.println("用户表修改结果: " + result1);
                }//判断订单是否已付款，若为真则同步返还账户余额
                int result2 = dao.change("order_state", 3, order.getOrder_number());
                System.out.println("订单表修改结果: " + result2);
                if (result1 != 0 && result2 > 0) {
                    success = true;
                    System.out.println("取消成功");
                } else {
                    success = false;
                    System.out.println("取消失败");
                    C3P0Util.rollbackTransaction();
                    System.out.println("回滚事务成功");
                }
            }
        } catch (SQLException e) {
            System.out.println("取消失败，" + e.getMessage());
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
    public Order show(Order order) {
        IOrderDao dao = new OrderDaoImpl();
        Order result = null;
        try {
            result = dao.find(order.getOrder_number());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Order> showList(Account account) {
        IOrderDao dao = new OrderDaoImpl();
        List<Order> result = null;
        try {
            result = dao.findList(account.getAccount_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Order> showList(Account account, Integer page, Integer limit) {
        IOrderDao dao = new OrderDaoImpl();
        List<Order> result = null;
        try {
            result = dao.findList(account.getAccount_name(),
                                  page, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Order> showAll() {
        IOrderDao dao = new OrderDaoImpl();
        List<Order> result = null;
        try {
            result = dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<String, Object> showAll(Integer page, Integer limit) {
        IOrderDao dao = new OrderDaoImpl();
        HashMap<String, Object> result = null;
        try {
            List<Order> orderList = dao.findAll(page, limit);
            int count = dao.findCount();
            result = new HashMap<>();
            result.put("beanList", orderList);
            result.put("count", count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
