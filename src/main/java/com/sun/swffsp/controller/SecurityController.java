package com.sun.swffsp.controller;

import com.sun.swffsp.dto.admin.query.UserCondition;
import com.sun.swffsp.dto.admin.query.base.PageCondition;
import com.sun.swffsp.dto.core.RoleDto;
import com.sun.swffsp.dto.core.UserDto;
import com.sun.swffsp.dto.admin.result.base.Response;
import com.sun.swffsp.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账户全选管理
 *
 * @author sun
 * @date 2019/2/21 10:52
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    /**
     * 用户信息
     * @return
     */
    @RequestMapping("/userInfo")
    public Object info() {
        return Response.success().data(securityService.info());
    }

    /**
     * 用户列表
     * @param condition
     * @return
     */
    @RequestMapping("/userList")
    public Object list(@RequestBody UserCondition condition){
        Page<UserDto> list = securityService.list(condition);
        return Response.success().data(list);
    }

    /**
     * 用户修改
     * @param user
     * @return
     */
    @RequestMapping("/save")
    public Object save(@RequestBody UserDto user){
        return securityService.save(user);
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @RequestMapping("/deleteUsers")
    public Object delete(@RequestBody List<String> ids){
        return securityService.delete(ids);
    }

    /**
     * 可用角色
     * @return
     */
    @RequestMapping("/roles")
    public Object roles(){
        List<RoleDto> roles = securityService.roles();
        return Response.success().data(roles);
    }



}
