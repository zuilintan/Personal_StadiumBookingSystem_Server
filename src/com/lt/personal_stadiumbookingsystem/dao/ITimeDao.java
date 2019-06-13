package com.lt.personal_stadiumbookingsystem.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 18:19
 * @版本: 1.0
 * @描述: //时段Dao接口
 * 1.0: Initial Commit
 */

public interface ITimeDao {
    //查询by场地号、场馆名(注意，此Time非DB的Table)
    List<Map<String, Object>> findList(String siteNumber, String gymName) throws SQLException;
}
