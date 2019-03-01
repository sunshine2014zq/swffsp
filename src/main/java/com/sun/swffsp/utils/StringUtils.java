package com.sun.swffsp.utils;

/**
 * 字符串工具类
 *
 * @author sun
 * @date 2019/3/1 17:05
 */
public class StringUtils extends org.springframework.util.StringUtils {

    /**
     * 把Null转为空字符串
     * @param obj
     * @return
     */
    public static String ifNullToEmptyStr(String obj) {
        return obj == null ? "" : obj;
    }
}
