package com.sun.swffsp.service;

import com.sun.swffsp.dto.req.UserCondition;
import com.sun.swffsp.dto.db.RoleEntity;
import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.dto.resp.Response;
import com.sun.swffsp.dto.resp.data.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 * 用户模块相关业务接口
 *
 * @author sun
 * @date 2019/2/21 10:57
 */
public interface SecurityService extends UserDetailsService {

    /**
     * 获取用户信息
     *
     * @return username, menu
     */
    UserInfo info();

    /**
     * 用户列表
     *
     * @param userCondition
     * @return
     */
    Page<UserEntity> list(UserCondition userCondition);

    /**
     * 保存<br>
     * id为空添加,不为空修改对象，值为空的属性不会被修改
     *
     * @param userEntity
     * @return
     */
    Response save(UserEntity userEntity);

    /**
     * 删除
     * @param ids
     * @return
     */
    Response delete(List<String> ids);

    /**
     * 可用角色
     * @return
     */
    List<RoleEntity> roles();
}
