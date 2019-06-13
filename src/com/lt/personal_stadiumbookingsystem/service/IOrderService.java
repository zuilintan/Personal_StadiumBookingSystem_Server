package com.lt.personal_stadiumbookingsystem.service;

import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.entity.Order;

import java.util.HashMap;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 21:36
 * @版本: 1.0
 * @描述: //订单Service接口
 * 1.0: Initial Commit
 */

public interface IOrderService {
    //创建订单。状态: 待付款
    Order book(Order order);

    //付款指定单号的订单。状态: 待执行
    boolean pay(Order order);

    //取消指定单号的订单。状态: 已取消
    boolean cancel(Order order);

    //展示指定单号的订单信息
    Order show(Order order);

    //展示指定账户的订单信息，倒序
    List<Order> showList(Account account);

    //展示指定账户的订单信息，倒序分页
    List<Order> showList(Account account, Integer page, Integer limit);

    //展示所有账户的订单信息(限管理员)，倒序
    List<Order> showAll();

    //展示所有账户的订单信息(限管理员)，倒序分页
    HashMap<String, Object> showAll(Integer page, Integer limit);
}
