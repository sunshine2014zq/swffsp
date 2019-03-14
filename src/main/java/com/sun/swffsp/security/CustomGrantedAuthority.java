package com.sun.swffsp.security;

import com.sun.swffsp.dto.core.RoleDto;
import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义权限验证
 *
 * @author sun
 * @date 2019/2/21 12:13
 */
public class CustomGrantedAuthority implements GrantedAuthority {

    private RoleDto role;

    public CustomGrantedAuthority(RoleDto role) {
        this.role = role;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getCode();
    }
}
