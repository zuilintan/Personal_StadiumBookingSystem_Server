package com.lt.personal_stadiumbookingsystem.util;

import java.util.UUID;

/**
 * @作者: LinTan
 * @日期: 2019/5/4 12:59
 * @版本: 1.0
 * @描述: //UUID的工具类
 * 1.0: Initial Commit
 */

public class UUIDUtil {

    private UUIDUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getOrderNumber() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return String.format("%012d", hashCode);//%: 通配符。0: 位数不足时补该数。12: 字符串长度强制为该数。d: 参数为正整数
    }//获取12位随机数
}
