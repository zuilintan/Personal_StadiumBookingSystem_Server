package com.lt.personal_stadiumbookingsystem.service;

import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.entity.Gym;

import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/28 17:00
 * @版本: 1.0
 * @描述: //场馆Service接口
 * 1.0: Initial Commit
 */

public interface IGymService {
    //维护场馆
    boolean vindicate(Gym gym);

    //营业场馆
    boolean operate(Gym gym);

    //展示所有场馆信息
    List<Gym> showAll();
}
