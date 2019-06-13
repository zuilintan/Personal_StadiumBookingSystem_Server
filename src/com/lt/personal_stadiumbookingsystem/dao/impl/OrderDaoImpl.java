package com.lt.personal_stadiumbookingsystem.dao.impl;

import com.lt.personal_stadiumbookingsystem.dao.IOrderDao;
import com.lt.personal_stadiumbookingsystem.dao.base.BaseDao;
import com.lt.personal_stadiumbookingsystem.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 21:17
 * @版本: 1.0
 * @描述: //订单Dao实现类
 * 1.0: Initial Commit
 */

public class OrderDaoImpl extends BaseDao implements IOrderDao {

    @Override
    public int add(String orderNumber, String orderDate, String orderTimeStart, String orderTimeEnd, String accountName, String siteNumber, String gymName) throws SQLException {
        String sql = "insert into sbs_order";
        sql += " (order_number, order_date, order_timestart, order_timeend, account_id, site_id)";
        sql += " values (?, ?, ?, ?,";
        sql += " (select account_id from sbs_account where account_name = ?),";
        sql += " (select site_id from sbs_site A, sbs_gym B where A.site_number = ? and A.gym_id = B.gym_id and B.gym_name = ?))";
        List<Object> list = new ArrayList<>();
        list.add(orderNumber);
        list.add(orderDate);
        list.add(orderTimeStart);
        list.add(orderTimeEnd);
        list.add(accountName);
        list.add(siteNumber);
        list.add(gymName);
        return executeUpdate(sql, list.toArray());
    }

    @Override
    public int remove(String orderNumber) throws SQLException {
        String sql = "delete from sbs_order where order_number = ?";
        return executeUpdate(sql, orderNumber);
    }

    @Override
    public <T> int change(String columnName, T value, String orderNumber) throws SQLException {
        String sql = "update sbs_order set " + columnName + " = ? where order_number = ?";
        List<Object> list = new ArrayList<>();
        list.add(value);
        list.add(orderNumber);
        return executeUpdate(sql, list.toArray());
    }

    @Override
    public <T> int change(String columnName, T value, T offset, String orderNumber) throws SQLException {
        String sql = "update sbs_order set " + columnName + " = " + value + " + " + offset;
        sql += " where order_number = ?";
        return executeUpdate(sql, orderNumber);
    }

    @Override
    public <T> int changeList(String columnName, T value, String orderDate, String orderTime, Integer orderState) throws SQLException {
        String sql = "update sbs_order set " + columnName + " = ?";
        sql += " where order_date like ? and order_timestart like ? and order_state like ?";
        List<Object> list = new ArrayList<>();
        list.add(value);
        list.add(orderDate);
        list.add(orderTime);
        list.add(orderState);
        return executeUpdate(sql, list.toArray());
    }

    @Override
    public int changeList(String orderDate, String orderTime, Integer orderState, String gymName) throws SQLException {
        String string = null;
        if (orderState == 0) {
            string = "A.order_state = 3";
        }
        if (orderState == 1) {
            string = "A.order_state = 3, B.account_coin = B.account_coin - D.gym_price";
        }
        String sql = "update sbs_order A, sbs_account B, sbs_site C, sbs_gym D";
        sql += " set " + string;
        sql += " where A.account_id = B.account_id and A.site_id = C.site_id and C.gym_id = D.gym_id";
        sql += " and A.order_date like ? and A.order_timestart like ? and A.order_state = ?";
        sql += " and D.gym_name like ?";
        List<Object> list = new ArrayList<>();
        list.add(orderDate);
        list.add(orderTime);
        list.add(orderState);
        list.add(gymName);
        return executeUpdate(sql, list.toArray());
    }

    @Override
    public Order find(String orderNumber) throws SQLException {
        String sql = "select * from sbs_order A, sbs_account B, sbs_site C, sbs_gym D";
        sql += " where A.account_id = B.account_id and A.site_id = C.site_id and C.gym_id = D.gym_id";
        sql += " and A.order_number = ?";
        return executeQueryForBean(sql, Order.class, orderNumber);
    }

    @Override
    public int find(String orderDate, String orderTimeStart, String orderTimeEnd, String siteNumber, String gymName, Integer orderState) throws SQLException {
        String sql = "select count(*) from sbs_order A, sbs_site B, sbs_gym C";
        sql += " where A.order_date = ? and A.order_timestart = ? and A.order_timeend = ?";
        sql += " and A.site_id = B.site_id and B.site_number = ? and B.gym_id = C.gym_id and C.gym_name = ? and order_state = ?";
        List<Object> list = new ArrayList<>();
        list.add(orderDate);
        list.add(orderTimeStart);
        list.add(orderTimeEnd);
        list.add(siteNumber);
        list.add(gymName);
        list.add(orderState);
        return executeQueryForScalar(sql, list.toArray()).intValue();
    }

    @Override
    public List<Order> findList(String accountName) throws SQLException {
        String sql = "select * from sbs_order A, sbs_account B, sbs_site C, sbs_gym D";
        sql += " where A.account_id = B.account_id and A.site_id = C.site_id and C.gym_id = D.gym_id";
        sql += " and B.account_name = ?";
        sql += " order by order_id desc";
        return executeQueryForBeanList(sql, Order.class, accountName);
    }

    @Override
    public List<Order> findList(String accountName, Integer page, Integer limit) throws SQLException {
        //select * from sbs_order where order_id >= (select order_id from sbs_order limit 0,1) limit 20;
        String sql = "select * from sbs_order A, sbs_account B, sbs_site C, sbs_gym D";
        sql += " where A.account_id = B.account_id and A.site_id = C.site_id and C.gym_id = D.gym_id";
        sql += " and B.account_name = ?";
        sql += " and A.order_id <= (select order_id from sbs_order order by order_id desc limit ?, 1)";
        sql += " order by order_id desc";
        sql += " limit ?";
        List<Object> list = new ArrayList<>();
        list.add(accountName);
        list.add(--page * limit);
        list.add(limit);
        return executeQueryForBeanList(sql, Order.class, list.toArray());
    }

    @Override
    public List<Order> findAll() throws SQLException {
        String sql = "select * from sbs_order A, sbs_account B, sbs_site C, sbs_gym D";
        sql += " where A.account_id = B.account_id and A.site_id = C.site_id and C.gym_id = D.gym_id";
        sql += " order by order_id desc";
        return executeQueryForBeanList(sql, Order.class);
    }

    @Override
    public List<Order> findAll(Integer page, Integer limit) throws SQLException {
        String sql = "select";
        sql += " A.order_id,";
        sql += " A.order_number,";
        sql += " A.order_date,";
        sql += " A.order_timestart,";
        sql += " A.order_timeend,";
        sql += " A.order_state,";
        sql += " B.account_name,";
        sql += " C.site_number,";
        sql += " D.gym_name,";
        sql += " D.gym_price";
        sql += " from sbs_order A, sbs_account B, sbs_site C, sbs_gym D";
        sql += " where A.account_id = B.account_id and A.site_id = C.site_id and C.gym_id = D.gym_id";
        sql += " and A.order_id <= (select order_id from sbs_order order by order_id desc limit ?, 1)";
        sql += " order by order_id desc";
        sql += " limit ?";
        List<Object> list = new ArrayList<>();
        list.add(--page * limit);
        list.add(limit);
        return executeQueryForBeanList(sql, Order.class, list.toArray());
    }

    @Override
    public int findCount() throws SQLException {
        String sql = "select count(*) from sbs_order";
        return executeQueryForScalar(sql).intValue();
    }
}
