package com.lt.personal_stadiumbookingsystem.service.impl;

import com.lt.personal_stadiumbookingsystem.dao.ISiteDao;
import com.lt.personal_stadiumbookingsystem.dao.impl.SiteDaoImpl;
import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.entity.Site;
import com.lt.personal_stadiumbookingsystem.service.ISiteService;

import java.sql.SQLException;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 0:18
 * @版本: 1.0
 * @描述: //场地Service实现类
 * 1.0: Initial Commit
 */

public class SiteServiceImpl implements ISiteService {

    @Override
    public List<Site> showList(Gym gym) {
        ISiteDao dao = new SiteDaoImpl();
        List<Site> result = null;
        try {
            result = dao.findList(gym.getGym_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
