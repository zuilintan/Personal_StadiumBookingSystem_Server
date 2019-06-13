package com.lt.personal_stadiumbookingsystem.dao.impl;

import com.lt.personal_stadiumbookingsystem.dao.ITimeDao;
import com.lt.personal_stadiumbookingsystem.dao.base.BaseDao;
import com.lt.personal_stadiumbookingsystem.entity.Time;
import com.lt.personal_stadiumbookingsystem.util.TimeUtil;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 18:20
 * @版本: 1.0
 * @描述: //时段Dao实现类
 * 1.0: Initial Commit
 */

public class TimeDaoImpl extends BaseDao implements ITimeDao {

    public List<Map<String, Object>> findList(String siteNumber, String gymName) throws SQLException {
        String sql = "select count(*) from sbs_order A, sbs_site B, sbs_gym C";
        sql += " where A.order_date = ? and A.order_timestart = ? and A.order_timeend = ? and A.order_state = ?";
        sql += " and A.site_id = B.site_id and B.site_number = ?";
        sql += " and B.gym_id = C.gym_id and C.gym_name = ?";

        List<Map<String, Object>> mapList = initThreeDayTime(siteNumber, gymName);
        for (Map<String, Object> map : mapList) {
            List<Time> timeList = GsonUtil.objectCastList(map.get("time"), Time.class);
            for (Time time : timeList) {
                String date = (String) map.get("date");
                String timeStart = time.getTime_start();
                String timeEnd = time.getTime_end();
                List<Object> list = new ArrayList<>();
                list.add(date);
                list.add(timeStart);
                list.add(timeEnd);
                list.add(1);
                list.add(siteNumber);
                list.add(gymName);
                int i = executeQueryForScalar(sql, list.toArray()).intValue();

                if (i > 0) {
                    time.setTime_orderstate(1);
                } else {
                    time.setTime_orderstate(0);
                }//标记已被预订的时段

                boolean isExpire = TimeUtil.isExpire(date + " " + timeStart,
                                                     TimeUtil.FORMAT_DATE_TIME);
                if (isExpire) {
                    time.setTime_expirestate(1);
                } else {
                    time.setTime_expirestate(0);
                }//标记已过期的时段
            }
        }
        return mapList;
    }//根据Order表的order_state，标记指定场地在指定日期时间下的状态

    public static List<Map<String, Object>> initThreeDayTime(String siteNumber, String gymName) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("gym", gymName);
            map.put("site", siteNumber);
            map.put("date", TimeUtil.getDate(i));
            map.put("time", initTime());
            mapList.add(map);
        }
        return mapList;
    }//初始化今天、明天、后天的时段

    public static List<Time> initTime() {
        List<Time> list = new ArrayList<>();
        Time time1 = new Time();
        time1.setTime_start("08:20:00");
        time1.setTime_end("10:00:00");
        time1.setTime_orderstate(0);
        list.add(time1);
        Time time2 = new Time();
        time2.setTime_start("10:05:00");
        time2.setTime_end("11:45:00");
        time2.setTime_orderstate(0);
        list.add(time2);
        Time time3 = new Time();
        time3.setTime_start("11:50:00");
        time3.setTime_end("13:30:00");
        time3.setTime_orderstate(0);
        list.add(time3);
        Time time4 = new Time();
        time4.setTime_start("13:35:00");
        time4.setTime_end("15:15:00");
        time4.setTime_orderstate(0);
        list.add(time4);
        Time time5 = new Time();
        time5.setTime_start("15:20:00");
        time5.setTime_end("17:00:00");
        time5.setTime_orderstate(0);
        list.add(time5);
        Time time6 = new Time();
        time6.setTime_start("17:05:00");
        time6.setTime_end("18:45:00");
        time6.setTime_orderstate(0);
        list.add(time6);
        Time time7 = new Time();
        time7.setTime_start("18:50:00");
        time7.setTime_end("20:30:00");
        time7.setTime_orderstate(0);
        list.add(time7);
        return list;
    }//初始化一天的时段
}
