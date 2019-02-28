package com.sun.swffsp.service;

import com.sun.swffsp.dto.condition.UserCondition;
import com.sun.swffsp.dto.db.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

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
    Object info();

    /**
     * 用户列表
     * @return
     * @param userCondition
     */
    Page<UserEntity> list(UserCondition userCondition);

    /**
     * 修改
     * @param userEntity
     * @return
     */
    Object modified(UserEntity userEntity);

    void delete(List<String> ids);
}
