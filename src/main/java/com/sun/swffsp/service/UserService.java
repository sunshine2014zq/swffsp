package com.sun.swffsp.service;

import com.sun.swffsp.dto.admin.param.PwdChangeParam;
import com.sun.swffsp.dto.admin.param.UserQuery;
import com.sun.swffsp.dto.admin.result.UserInfoResult;
import com.sun.swffsp.dto.admin.result.base.Response;
import com.sun.swffsp.dto.core.RoleDto;
import com.sun.swffsp.dto.core.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 用户模块相关业务接口
 *
 * @author sun
 * @date 2019/2/21 10:57
 */
public interface UserService extends UserDetailsService {

    /**
     * 获取用户信息
     *
     * @return username, menu
     */
    UserInfoResult info();

    /**
     * 用户列表
     *
     * @param condition
     * @return
     */
    Page<UserDto> list(UserQuery condition);

    /**
     * 保存<br>
     * id为空添加,不为空修改对象，值为空的属性不会被修改
     *
     * @param user
     * @return
     */
    Response save(UserDto user);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    Response delete(List<String> ids);

    /**
     * 可用角色
     *
     * @return
     */
    List<RoleDto> roles();

    /**
     * 修改密码
     *
     * @param param
     * @return
     */
    Response pwdChange(PwdChangeParam param);
}
