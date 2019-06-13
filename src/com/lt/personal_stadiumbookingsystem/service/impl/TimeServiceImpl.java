package com.lt.personal_stadiumbookingsystem.service.impl;

import com.lt.personal_stadiumbookingsystem.dao.ITimeDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.TimeDaoImpl;
import com.lt.personal_stadiumbookingsystem.entity.Site;
import com.lt.personal_stadiumbookingsystem.service.ITimeService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 18:17
 * @版本: 1.0
 * @描述: //时段Service实现类
 * 1.0: Initial Commit
 */

public class TimeServiceImpl implements ITimeService {

    @Override
    public List<Map<String, Object>> showList(Site site) {
        ITimeDao dao = new TimeDaoImpl();
        List<Map<String, Object>> result = null;
        try {
            result = dao.findList(site.getSite_number(), site.getGym_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
