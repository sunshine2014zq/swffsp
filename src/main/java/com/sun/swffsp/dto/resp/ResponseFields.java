package com.sun.swffsp.dto.resp;

/**
 * 响应字段
 *
 * @author sun
 * @date 2019/2/25 17:46
 */
public class ResponseFields {

    /**
     * 通用的JSON请求响应
     */
    public static class Common {
        /**
         * 状态
         */
        public static final String STATUS = "status";
        /**
         * 消息
         */
        public static final String MESSAGE = "msg";
    }

    /**
     * 增删改查
     */
    public static class CRUD extends Common {

        /**
         * 字段错误信息 name:msg
         */
        public static final String FIELD_ERROR = "fieldErr";

    }


}
