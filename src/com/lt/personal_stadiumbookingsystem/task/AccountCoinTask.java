package com.lt.personal_stadiumbookingsystem.task;

import com.lt.personal_stadiumbookingsystem.dao.IAccountDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.AccountDaoImpl;
import com.lt.personal_stadiumbookingsystem.util.C3P0Util;

import java.sql.SQLException;
import java.util.TimerTask;

/**
 * @作者: LinTan
 * @日期: 2019/5/14 14:13
 * @版本: 1.0
 * @描述: //定时任务
 * 1.0: Initial Commit
 */

public class AccountCoinTask extends TimerTask {

    @Override
    public void run() {
        try {
            C3P0Util.beginTransaction();
            System.out.println("事务开启成功");
            IAccountDao accountDao = new AccountDaoImpl();

            System.out.println("账户任务1: 账户余额每日重置为1000");
            int i = accountDao.change("account_coin", "1000", "%");
            System.out.println("账户任务1修改的数据量: " + i);

            System.out.println("账户的定时任务执行成功");
        } catch (Exception e) {
            System.out.println("账户的定时任务执行失败" + e.getMessage());
            C3P0Util.rollbackTransaction();
            System.out.println("事务回滚成功");
        } finally {
            C3P0Util.commitTransaction();
            System.out.println("事务提交成功");
        }
    }
}
