package com.lt.personal_stadiumbookingsystem.util;

/**
 * @作者: LinTan
 * @日期: 2019/5/3 16:46
 * @版本: 1.0
 * @描述: //打印的工具类
 * 1.0: Initial Commit
 */

public class PrintUtil {
    public static final boolean isEnable = true;

    private PrintUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void printMethodName() {
        if (isEnable) {
            String className = Thread.currentThread().getStackTrace()[2].getClassName();
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            System.out.println("ClassName: " + className + ", Method: " + methodName);
        }
    }//打印调用者的类名，方法名
}
