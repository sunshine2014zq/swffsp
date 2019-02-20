package com.sun.swffsp.entity;

/**
 * JSON登录结果相应实体
 *
 * @author sun
 * @date 2019/2/19 14:02
 */
public class LoginEntity {

    /**
     * 成功 / 失败
     */
    private boolean status;

    private String msg;

    public LoginEntity(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
