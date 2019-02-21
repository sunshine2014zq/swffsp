package com.sun.swffsp.controller;

import com.sun.swffsp.entity.database.RoleEntity;
import com.sun.swffsp.entity.database.UserEntity;
import com.sun.swffsp.security.CustomGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户模块
 *
 * @author sun
 * @date 2019/2/21 10:52
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public UserEntity getUserInfo(Authentication authentication) {
        UserEntity user = new UserEntity();
        List<CustomGrantedAuthority> authorities =
                (List<CustomGrantedAuthority>) authentication.getAuthorities();
        List<RoleEntity> roles = new ArrayList<>();
        for (CustomGrantedAuthority authority : authorities) {
            roles.add(authority.getRole());
        }
        user.setRoles(roles);
        user.setUsername(authentication.getName());
        return user;
    }

}
