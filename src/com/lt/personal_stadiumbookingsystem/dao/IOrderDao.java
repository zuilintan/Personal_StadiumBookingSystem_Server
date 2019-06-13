package com.lt.personal_stadiumbookingsystem.dao;

import com.lt.personal_stadiumbookingsystem.entity.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 21:18
 * @版本: 1.0
 * @描述: //订单Dao接口
 * 1.0: Initial Commit
 */

public interface IOrderDao {
    //增加
    int add(String orderNumber, String orderDate, String orderTimeStart, String orderTimeEnd, String accountName, String siteNumber, String gymName) throws SQLException;

    //删除
    int remove(String orderNumber) throws SQLException;

    //改动
    <T> int change(String columnName, T value, String orderNumber) throws SQLException;

    //改动，带偏移量(一般用于在原有值的基础上修改)
    <T> int change(String columnName, T value, T offset, String orderNumber) throws SQLException;

    //改动，批量修改
    <T> int changeList(String columnName, T value, String orderDate, String orderTime, Integer orderState) throws SQLException;

    //改动，批量修改订单状态，并按需改动用户表硬币数
    int changeList(String orderDate, String orderTime, Integer orderState, String gymName) throws SQLException;

    //查询by多列
    int find(String orderDate, String orderTimeStart, String orderTimeEnd, String siteNumber, String gymName, Integer orderState) throws SQLException;

    //查询by订单号
    Order find(String orderNumber) throws SQLException;

    //查询by账户名，倒序byId
    List<Order> findList(String accountName) throws SQLException;

    //查询by账户名，倒序byId，分页byId
    List<Order> findList(String accountName, Integer page, Integer limit) throws SQLException;

    //查询全部，倒序byId
    List<Order> findAll() throws SQLException;

    //查询全部，倒序byId，分页byId
    List<Order> findAll(Integer page, Integer limit) throws SQLException;

    //查询总数
    int findCount() throws SQLException;
}
