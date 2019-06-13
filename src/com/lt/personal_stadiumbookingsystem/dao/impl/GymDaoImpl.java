package com.lt.personal_stadiumbookingsystem.dao.impl;

import com.lt.personal_stadiumbookingsystem.dao.base.BaseDao;
import com.lt.personal_stadiumbookingsystem.dao.IGymDao;
import com.lt.personal_stadiumbookingsystem.entity.Gym;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/28 16:57
 * @版本: 1.0
 * @描述: //场馆Dao实现类
 * 1.0: Initial Commit
 */

public class GymDaoImpl extends BaseDao implements IGymDao {

    @Override
    public <T> int change(String columnName, T value, String gymName) throws SQLException {
        String sql = "update sbs_gym set " + columnName + " = ? where gym_name like ?";
        List<Object> list = new ArrayList<>();
        list.add(value);
        list.add(gymName);
        return executeUpdate(sql, list.toArray());
    }

    @Override
    public Gym find(String gymName) throws SQLException {
        String sql = "select * from sbs_gym where gym_name = ?";
        return executeQueryForBean(sql, Gym.class, gymName);
    }

    @Override
    public List<Gym> findAll() throws SQLException {
        String sql = "select * from sbs_gym";
        return executeQueryForBeanList(sql, Gym.class);
    }
}
