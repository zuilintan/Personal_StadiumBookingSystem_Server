package com.lt.personal_stadiumbookingsystem.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 23:47
 * @版本: 1.0
 * @描述: //C3P0的工具类
 * 1.0: Initial Commit
 */

public class C3P0Util {
    private static ComboPooledDataSource sDataSource = new ComboPooledDataSource("db_sbs");//创建一个连接池数据源
    private static ThreadLocal<Connection> sThreadLocal = new ThreadLocal<>();//创建一个局部线程

    private C3P0Util() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = sThreadLocal.get();
            if (connection == null) {
                connection = sDataSource.getConnection();//获取新的连接
                sThreadLocal.set(connection);//绑定连接到局部线程
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }//获取当前线程绑定的连接

    public static void releaseConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                sThreadLocal.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//释放当前线程绑定的连接

    public static void beginTransaction() {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//开启事务

    public static void rollbackTransaction() {
        try {
            Connection connection = getConnection();
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//回滚事务

    public static void commitTransaction() {
        try {
            Connection connection = getConnection();
            connection.commit();
            releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//提交事务
}
