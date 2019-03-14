package com.sun.swffsp.dto.admin.result.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口响应实体
 *
 * @author sun
 * @date 2019/3/13 11:10
 */
@Data
public class Response<T> implements Serializable {

    /**
     * 响应代码
     */
    private String code;

    /**
     * 响应代码-成功
     */
    public static final String CODE_SUCCESS = "success";

    /**
     * 响应代码-失败
     */
    public static final String CODE_FAIL = "fail";

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    private Response() {
    }

    /**
     * 根据响应状态和消息创建一个对象
     * @param code
     * @param message
     * @return
     */
    public static Response create(String code, String message){
        Response response = new Response();
        response.code = code;
        response.message = message;
        return response;
    }

    /**
     * 根据消息创建一个状态为:请求成功 的对象
     * @param message
     * @return
     */
    public static Response success(String message){
        return create(CODE_SUCCESS,message);
    }

    /**
     * 创建一个无消息的成功请求
     * @return
     */
    public static Response success(){
        return create(CODE_SUCCESS,null);
    }

    /**
     * 根据消息创建一个状态为：请求失败 的对象
     * @param message
     * @return
     */
    public static Response fail(String message){
        return create(CODE_FAIL,message);
    }

    /**
     * 创建一个无消息的成功请求
     * @return
     */
    public static Response fail(){
        return create(CODE_FAIL,null);
    }

    /**
     * 装配data区数据
     * @param data
     * @return
     */
    public Response data(T data){
        this.data = data;
        return this;
    }

}
