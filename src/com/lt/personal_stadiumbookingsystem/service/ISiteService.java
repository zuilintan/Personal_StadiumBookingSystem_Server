package com.lt.personal_stadiumbookingsystem.service;

import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.entity.Site;

import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 0:17
 * @版本: 1.0
 * @描述: //场地Service接口
 * 1.0: Initial Commit
 */

public interface ISiteService {
    //展示指定场馆的场地信息
    List<Site> showList(Gym gym);
}
