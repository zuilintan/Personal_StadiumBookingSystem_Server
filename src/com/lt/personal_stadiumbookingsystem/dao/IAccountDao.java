package com.lt.personal_stadiumbookingsystem.dao;

import com.lt.personal_stadiumbookingsystem.entity.Account;

import java.sql.SQLException;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 23:49
 * @版本: 1.0
 * @描述: //用户Dao接口
 * 1.0: Initial Commit
 */

public interface IAccountDao {
    //增加
    int add(String accountName, String accountPasswd, String accountSex) throws SQLException;

    //删除
    int remove(String accountName) throws SQLException;

    //改动
    <T> int change(String columnName, T value, String accountName) throws SQLException;

    //改动，带偏移量(一般用于在原有值的基础上修改)
    <T> int change(String columnName, T value, T offset, String accountName) throws SQLException;

    //查询by账户名
    Account find(String accountName) throws SQLException;

    //查询by账户名、角色
    Account find(String accountName, String accountRole) throws SQLException;

    //查询by账户名、账户密码、角色
    Account find(String accountName, String accountPasswd, String accountRole) throws SQLException;

    //查询全部，倒序byId
    List<Account> findAll() throws SQLException;

    //查询全部，倒序byId，分页
    List<Account> findAll(Integer page, Integer limit) throws SQLException;

    //查询总数
    int findCount() throws SQLException;
}
