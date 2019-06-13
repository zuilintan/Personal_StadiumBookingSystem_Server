package com.lt.personal_stadiumbookingsystem.dao.base;

import com.lt.personal_stadiumbookingsystem.util.C3P0Util;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/23 0:03
 * @版本: 1.0
 * @描述: //BaseDao
 * 源址: https://blog.csdn.net/a911711054/article/details/77719685
 * 1.0: Initial Commit
 */

public class BaseDao {
    private QueryRunner mQueryRunner = new QueryRunner();

    public int executeUpdate(String sql, Object... args) throws SQLException {
        Connection connection = C3P0Util.getConnection();
        return mQueryRunner.update(connection, sql, args);
    }//增、删、改(若返回值>0，即成功)

    public <T> T executeQueryForBean(String sql, Class<T> cls, Object... args) throws SQLException {
        Connection connection = C3P0Util.getConnection();
        return mQueryRunner.query(connection, sql, new BeanHandler<>(cls), args);
    }//1.查，将结果集的第一行数据，封装为JavaBean对象

    public <T> List<T> executeQueryForBeanList(String sql, Class<T> cls, Object... args) throws SQLException {
        Connection connection = C3P0Util.getConnection();
        return mQueryRunner.query(connection, sql, new BeanListHandler<>(cls), args);
    }//2.查，将结果集的每一行数据，封装为JavaBean对象，多个JavaBean对象封装到List集合中

    public List<Object> executeQueryForColumnList(String sql, String columnName, Object... args) throws SQLException {
        Connection connection = C3P0Util.getConnection();
        return mQueryRunner.query(connection, sql, new ColumnListHandler<>(columnName), args);
    }//3.查，存储指定列的数据，注意，List<Object>中每列数据的类型不同

    public Long executeQueryForScalar(String sql, Object... args) throws SQLException {
        Connection connection = C3P0Util.getConnection();
        return mQueryRunner.query(connection, sql, new ScalarHandler<>(), args);
    }//4.查，针对查询后有唯一结果的情况，常用于聚合函数。eg: "select count(*) from user"

    public Map<String, Object> executeQueryForMap(String sql, Object... args) throws SQLException {
        Connection connection = C3P0Util.getConnection();
        return mQueryRunner.query(connection, sql, new MapHandler(), args);
    }//5.查，将结果集的第一行数据，存储到Map集合中。格式: Map<Key, Value>，Key: 列名，Value: 该列数据

    public List<Map<String, Object>> executeQueryForMapList(String sql, Object... args) throws SQLException {
        Connection connection = C3P0Util.getConnection();
        return mQueryRunner.query(connection, sql, new MapListHandler(), args);
    }//6.查，将结果集的每一行数据，存储到Map集合中，再将多个Map集合存储到List集合中。格式: Map<Key, Value>，Key: 列名，Value: 该列数据。
}
