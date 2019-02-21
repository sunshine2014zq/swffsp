package com.sun.swffsp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sun.swffsp.entity.database.PrivilegeEntity;
import com.sun.swffsp.entity.database.RoleEntity;
import com.sun.swffsp.entity.database.UserEntity;
import com.sun.swffsp.jpa.UserJPA;
import com.sun.swffsp.security.CustomGrantedAuthority;
import com.sun.swffsp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 用户业务
 *
 * @author sun
 * @date 2019/1/10 14:20
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserJPA userJPA;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userJPA.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("为查找到用户：" + s);
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


    @Override
    public Object getUserInfo() {
        JSONObject result = new JSONObject();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        result.put("username",auth.getName());
        //合并所有权限-去重
        List<PrivilegeEntity> privileges = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<CustomGrantedAuthority> authorities = (List<CustomGrantedAuthority>) auth.getAuthorities();
        for (CustomGrantedAuthority authority : authorities) {
            List<PrivilegeEntity> privilegeList = authority.getRole().getPrivileges();
            if(privilegeList != null){
                for (PrivilegeEntity privilege : privilegeList){
                    if(!codes.contains(privilege.getCode())){
                        privileges.add(privilege);
                    }
                }
            }
        }
        //将权限排成树形菜单
        List<JSONObject> menus =menuTree(privileges);
        result.put("menus",menus);
        return result;
    }

    /**
     * 将权限排成树形菜单
     * @param privileges
     * @return
     */
    private List<JSONObject> menuTree(List<PrivilegeEntity> privileges) {
        List<JSONObject> menus = new ArrayList<>();
        Iterator<PrivilegeEntity> iterator = privileges.iterator();
        while (iterator.hasNext()){
            PrivilegeEntity privilege = iterator.next();
            if(privilege.getType().equals(PrivilegeEntity.TYPE_MENU_1)){
                //一级菜单
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(privilege));
                //为一级菜单查找子菜单
                List<PrivilegeEntity> subMenus = new ArrayList<>();
                privileges.forEach(p->{
                    if (privilege.getCode().equals(p.getParentCode())){
                        subMenus.add(p);
                    }
                });
                object.put("subMenus",subMenus);
                menus.add(object);
                iterator.remove();
//                privileges.removeAll(subMenus);
            }
        }
        return menus;
    }
}
