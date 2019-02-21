package com.sun.swffsp.service;

/**
 * 用户模块相关业务接口
 *
 * @author sun
 * @date 2019/2/21 10:57
 */
public interface UserService {

    /**
     * 获取用户信息
     * @return username,menu
     */
    Object getUserInfo();
}
