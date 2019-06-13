package com.lt.personal_stadiumbookingsystem.dao.impl;

import com.lt.personal_stadiumbookingsystem.dao.IAccountDao;
import com.lt.personal_stadiumbookingsystem.dao.base.BaseDao;
import com.lt.personal_stadiumbookingsystem.entity.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 23:49
 * @版本: 1.0
 * @描述: //账户Dao实现类
 * 1.0: Initial Commit
 */

public class AccountDaoImpl extends BaseDao implements IAccountDao {

    @Override
    public int add(String accountName, String accountPasswd, String accountSex) throws SQLException {
        String sql = "insert into sbs_account (account_name, account_passwd, account_sex) values (?, ?, ?)";
        List<Object> list = new ArrayList<>();
        list.add(accountName);
        list.add(accountPasswd);
        list.add(accountSex);
        return executeUpdate(sql, list.toArray());
    }

    @Override
    public int remove(String accountName) throws SQLException {
        String sql = "delete from sbs_account where account_name = ?";
        return executeUpdate(sql, accountName);
    }

    @Override
    public <T> int change(String columnName, T value, String accountName) throws SQLException {
        String sql = "update sbs_account set " + columnName + " = ? where account_name like ?";
        List<Object> list = new ArrayList<>();
        list.add(value);
        list.add(accountName);
        return executeUpdate(sql, list.toArray());
    }

    @Override
    public <T> int change(String columnName, T value, T offset, String accountName) throws SQLException {
        String sql = "update sbs_account set " + columnName + " = " + value + " + " + offset;
        sql += " where account_name like ?";
        return executeUpdate(sql, accountName);
    }

    @Override
    public Account find(String accountName) throws SQLException {
        return find(accountName, "%");
    }

    @Override
    public Account find(String accountName, String accountRole) throws SQLException {
        return find(accountName, "%", accountRole);
    }

    @Override
    public Account find(String accountName, String accountPasswd, String accountRole) throws SQLException {
        String sql = "select * from sbs_account";
        sql += " where account_name like ? and account_passwd like ? and account_role like ?";
        List<Object> list = new ArrayList<>();
        list.add(accountName);
        list.add(accountPasswd);
        list.add(accountRole);
        return executeQueryForBean(sql, Account.class, list.toArray());
    }

    @Override
    public List<Account> findAll() throws SQLException {
        String sql = "select";
        sql += " account_id,";
        sql += " account_name,";
        sql += " account_nickname,";
        sql += " account_realname,";
        sql += " account_coin,";
        sql += " account_sex,";
        sql += " account_phone,";
        sql += " account_email,";
        sql += " account_qq,";
        sql += " from sbs_account where account_role != 'admin'";
        sql += " order by account_id desc";
        return executeQueryForBeanList(sql, Account.class);
    }

    @Override
    public List<Account> findAll(Integer page, Integer limit) throws SQLException {
        String sql = "select";
        sql += " account_id,";
        sql += " account_name,";
        sql += " account_nickname,";
        sql += " account_realname,";
        sql += " account_coin,";
        sql += " account_sex,";
        sql += " account_phone,";
        sql += " account_email,";
        sql += " account_qq";
        sql += " from sbs_account where account_role != 'admin'";
        sql += " and account_id<=(select account_id from sbs_account order by account_id desc limit ?, 1)";
        sql += " order by account_id desc";
        sql += " limit ?";
        List<Object> list = new ArrayList<>();
        list.add(--page * limit);
        list.add(limit);
        return executeQueryForBeanList(sql, Account.class, list.toArray());
    }

    @Override
    public int findCount() throws SQLException {
        String sql = "select count(*) from sbs_account";
        return executeQueryForScalar(sql).intValue();
    }
}
