package com.sun.swffsp.dto.resp;

/**
 * 接口响应实体
 *
 * @author sun
 * @date 2019/3/13 11:10
 */
public class Response {

    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 响应状态-操作成功
     */
    public static final Integer STATUS_OK = 200;

    /**
     * 响应状态-操作失败
     */
    public static final Integer STATUS_FAIL = 400;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private Object data;

    private Response() {
    }

    /**
     * 根据响应状态和消息创建一个对象
     * @param status
     * @param message
     * @return
     */
    public static Response create(Integer status, String message){
        Response response = new Response();
        response.status = status;
        response.message = message;
        return response;
    }

    /**
     * 根据消息创建一个状态为:请求成功 的对象
     * @param message
     * @return
     */
    public static Response ok(String message){
        return create(STATUS_OK,message);
    }

    /**
     * 创建一个无消息的成功请求
     * @return
     */
    public static Response ok(){
        return create(STATUS_OK,null);
    }

    /**
     * 根据消息创建一个状态为：请求失败 的对象
     * @param message
     * @return
     */
    public static Response fail(String message){
        return create(STATUS_FAIL,message);
    }

    /**
     * 装配data区数据
     * @param data
     * @return
     */
    public Response data(Object data){
        this.data = data;
        return this;
    }

    /**
     * 按照clazz的格式将数据装配到data区
     * @param data
     * @param clazz
     * @return
     */
    public Response data(Object data, Class clazz){
//        JSONObject.parseObject("");
//        JSON.parseObject("")
        return this;
    }





    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
