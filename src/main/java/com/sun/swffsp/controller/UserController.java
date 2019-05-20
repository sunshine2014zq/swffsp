package com.sun.swffsp.controller;

import com.sun.swffsp.dto.admin.param.PwdChangeParam;
import com.sun.swffsp.dto.admin.param.UserQuery;
import com.sun.swffsp.dto.admin.result.base.Response;
import com.sun.swffsp.dto.core.RoleDto;
import com.sun.swffsp.dto.core.UserDto;
import com.sun.swffsp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账户管理
 *
 * @author sun
 * @date 2019/2/21 10:52
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户信息
     *
     * @return
     */
    @RequestMapping("/info")
    public Object userInfo() {
        return Response.success().data(userService.info());
    }

    /**
     * 用户列表
     *
     * @param condition
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestBody UserQuery condition) {
        Page<UserDto> list = userService.list(condition);
        return Response.success().data(list);
    }

    /**
     * 用户保存
     *
     * @param user
     * @return
     */
    @RequestMapping("/save")
    public Object save(@RequestBody UserDto user) {
        return userService.save(user);
    }

    /**
     * 用户删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Object delete(@RequestBody List<String> ids) {
        return userService.delete(ids);
    }

    /**
     * 修改密码
     *
     * @param param
     * @return
     */
    @RequestMapping("/pwdChange")
    public Object pwdChange(@RequestBody PwdChangeParam param) {
        return userService.pwdChange(param);
    }












    /**
     * 可用角色
     *
     * @return
     */
    @RequestMapping("/roles")
    public Object roles() {
        List<RoleDto> roles = userService.roles();
        return Response.success().data(roles);
    }


}
