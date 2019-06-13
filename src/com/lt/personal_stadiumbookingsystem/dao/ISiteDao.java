package com.lt.personal_stadiumbookingsystem.dao;

import com.lt.personal_stadiumbookingsystem.entity.Site;

import java.sql.SQLException;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 0:20
 * @版本: 1.0
 * @描述: //场地Dao接口
 * 1.0: Initial Commit
 */

public interface ISiteDao {
    //查询by场地号、场馆名
    Site find(String siteNumber, String gymName) throws SQLException;

    //查询by场馆名
    List<Site> findList(String gymName) throws SQLException;
}
