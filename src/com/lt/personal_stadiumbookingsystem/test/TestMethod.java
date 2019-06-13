package com.lt.personal_stadiumbookingsystem.test;

import com.lt.personal_stadiumbookingsystem.task.OrderStateTimer1;

import java.util.Calendar;

/**
 * @作者: LinTan
 * @日期: 2019/5/23 0:52
 * @版本: 1.0
 * @描述: //
 * 1.0: Initial Commit
 */

public class TestMethod {
    public static void main(String[] args) {
        int i1 = 1;
        int i2 = 2;
        System.out.println(" " + i1 + i2);
        System.out.println(" " + (i1 + i2));
        System.out.println(i1 + i2 + " ");

        String a = "";
        System.out.println("1" + a + "2");
        System.out.println(i1 + i2 + a + i1 + i2);

        String b = "字符串";
        String string1 = b;
        String string2 = b.toString();
        System.out.println(string1==string2);
        System.out.println(string1.equals(string2));

        Calendar calendar1 = Calendar.getInstance();
        //定制开始执行时间，每日08:20:00
        calendar1.set(Calendar.HOUR_OF_DAY, 8);
        calendar1.set(Calendar.MINUTE, 20);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.getTime();
        System.out.println(calendar1);

        new OrderStateTimer1();
    }
}
