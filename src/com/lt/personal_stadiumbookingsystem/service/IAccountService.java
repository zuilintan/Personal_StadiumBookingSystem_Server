package com.lt.personal_stadiumbookingsystem.service;


import com.lt.personal_stadiumbookingsystem.entity.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 23:48
 * @版本: 1.0
 * @描述: //用户Service接口
 * 1.0: Initial Commit
 */

public interface IAccountService {
    //注册账户
    boolean register(Account account);

    //登录账户
    boolean login(Account account);

    //充值账户
    boolean append(Float offset, Account account);

    //编辑指定账户的信息
    boolean edit(Map<String, String[]> parameterMap, Account account);

    //展示指定账户的信息
    Account show(Account account);

    //展示所有账户的信息(限管理员)，倒序
    List<Account> showAll();

    //展示所有账户的信息(限管理员)，倒序分页
    HashMap<String, Object> showAll(Integer page, Integer limit);
}
