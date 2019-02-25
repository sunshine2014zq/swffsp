package com.sun.swffsp.dto.resp;

/**
 * 自定义的JSON对象,对fastjson中的JSONObject扩展
 *
 * @author sun
 * @date 2019/2/25 17:38
 */
public class JSONObject extends com.alibaba.fastjson.JSONObject {

    @Override
    public JSONObject put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
