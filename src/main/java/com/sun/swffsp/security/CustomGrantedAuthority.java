package com.sun.swffsp.security;

import com.sun.swffsp.entity.database.RoleEntity;
import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义权限验证
 *
 * @author sun
 * @date 2019/2/21 12:13
 */
public class CustomGrantedAuthority implements GrantedAuthority {

    private RoleEntity role;

    public CustomGrantedAuthority(RoleEntity role) {
        this.role = role;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getCode();
    }
}
