package com.lt.personal_stadiumbookingsystem.dao.impl;

import com.lt.personal_stadiumbookingsystem.dao.base.BaseDao;
import com.lt.personal_stadiumbookingsystem.dao.ISiteDao;
import com.lt.personal_stadiumbookingsystem.entity.Site;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 0:21
 * @版本: 1.0
 * @描述: //场地Dao实现类
 * 1.0: Initial Commit
 */

public class SiteDaoImpl extends BaseDao implements ISiteDao {

    @Override
    public Site find(String siteNumber, String gymName) throws SQLException {
        String sql = "select * from sbs_site A, sbs_gym B";
        sql += " where A.site_number = ? and A.gym_id = B.gym_id and B.gym_name = ?";
        List<Object> list = new ArrayList<>();
        list.add(siteNumber);
        list.add(gymName);
        return executeQueryForBean(sql, Site.class, list.toArray());
    }

    @Override
    public List<Site> findList(String gymName) throws SQLException {
        String sql = "select * from sbs_site A, sbs_gym B";
        sql += " where A.gym_id = B.gym_id and B.gym_name = ?";
        return executeQueryForBeanList(sql, Site.class, gymName);
    }
}
