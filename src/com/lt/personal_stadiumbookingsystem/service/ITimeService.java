package com.lt.personal_stadiumbookingsystem.service;

import com.lt.personal_stadiumbookingsystem.entity.Site;

import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 18:16
 * @版本: 1.0
 * @描述: //时段Service接口
 * 1.0: Initial Commit
 */

public interface ITimeService {
    //展示指定场地、日期、时间下的空闲时段
    List<Map<String, Object>> showList(Site site);
}
