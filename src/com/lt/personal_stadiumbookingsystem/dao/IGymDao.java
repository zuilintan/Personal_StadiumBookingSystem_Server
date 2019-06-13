package com.lt.personal_stadiumbookingsystem.dao;

import com.lt.personal_stadiumbookingsystem.entity.Gym;

import java.sql.SQLException;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/28 16:56
 * @版本: 1.0
 * @描述: //场馆Dao接口
 * 1.0: Initial Commit
 */

public interface IGymDao {

    //改动
    <T> int change(String columnName, T value, String gymName) throws SQLException;

    //查询by场馆名
    Gym find(String gymName) throws SQLException;

    //查询全部
    List<Gym> findAll() throws SQLException;
}
