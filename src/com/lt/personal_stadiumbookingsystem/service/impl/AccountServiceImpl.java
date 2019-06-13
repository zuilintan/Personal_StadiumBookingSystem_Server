package com.lt.personal_stadiumbookingsystem.service.impl;

import com.lt.personal_stadiumbookingsystem.dao.IAccountDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.AccountDaoImpl;
import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.service.IAccountService;
import com.lt.personal_stadiumbookingsystem.util.C3P0Util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 23:48
 * @版本: 1.0
 * @描述: //用户Service实现类
 * 1.0: Initial Commit
 */

public class AccountServiceImpl implements IAccountService {

    @Override
    public boolean register(Account account) {
        IAccountDao dao = new AccountDaoImpl();
        boolean success = false;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            int result = dao.add(account.getAccount_name(), account.getAccount_passwd(), account.getAccount_sex());
            if (result > 0) {
                success = true;
                System.out.println("注册成功");
            } else {
                success = false;
                System.out.println("注册失败");
                C3P0Util.rollbackTransaction();
                System.out.println("回滚事务成功");
            }
        } catch (SQLException e) {
            System.out.println("注册失败，" + e.getMessage());
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
    public boolean login(Account account) {
        IAccountDao dao = new AccountDaoImpl();
        boolean success = false;
        try {
            Account result = dao.find(account.getAccount_name(),
                                      account.getAccount_passwd(),
                                      account.getAccount_role());
            if (result != null) {
                success = true;
                System.out.println("登录成功");
            } else {
                success = false;
                System.out.println("登录失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean append(Float offset, Account account) {
        IAccountDao dao = new AccountDaoImpl();
        boolean success = false;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            int result = dao.change("account_coin", "account_coin", offset, account.getAccount_name());
            if (result > 0) {
                success = true;
                System.out.println("充值成功");
            } else {
                success = false;
                System.out.println("充值失败");
                C3P0Util.rollbackTransaction();
                System.out.println("回滚事务成功");
            }
        } catch (SQLException e) {
            System.out.println("充值失败，" + e.getMessage());
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
    public boolean edit(Map<String, String[]> parameterMap, Account account) {
        IAccountDao dao = new AccountDaoImpl();
        boolean success = true;
        try {
            C3P0Util.beginTransaction();
            System.out.println("开启事务成功");
            String accountName = account.getAccount_name();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                if (!"action".equals(key) && !"account_name".equals(key)) {
                    String value = parameterMap.get(key)[0];
                    int result = dao.change(key, value, accountName);
                    if (result > 0) {
                        System.out.println(key + ": 编辑成功");
                    } else {
                        success = false;
                        System.out.println(key + ": 编辑失败");
                        C3P0Util.rollbackTransaction();
                        System.out.println("回滚事务成功");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("编辑失败，" + e.getMessage());
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
    public Account show(Account account) {
        IAccountDao dao = new AccountDaoImpl();
        Account result = null;
        try {
            result = dao.find(account.getAccount_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Account> showAll() {
        IAccountDao dao = new AccountDaoImpl();
        List<Account> result = null;
        try {
            result = dao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<String, Object> showAll(Integer page, Integer limit) {
        IAccountDao dao = new AccountDaoImpl();
        HashMap<String, Object> result = null;
        try {
            List<Account> accountList = dao.findAll(page, limit);
            int count = dao.findCount();
            result = new HashMap<>();
            result.put("beanList", accountList);
            result.put("count", count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
