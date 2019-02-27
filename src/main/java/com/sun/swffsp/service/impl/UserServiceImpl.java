package com.sun.swffsp.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.sun.swffsp.dto.condition.UserCondition;
import com.sun.swffsp.dto.db.PrivilegeEntity;
import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.jpa.UserJPA;
import com.sun.swffsp.security.CustomGrantedAuthority;
import com.sun.swffsp.service.UserService;
import com.sun.swffsp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用户业务
 *
 * @author sun
 * @date 2019/1/10 14:20
 */
@Service
public class UserServiceImpl extends BaseService implements UserDetailsService, UserService {

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
    public Object info() {
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

    @Override
    public Page<UserEntity> list(UserCondition userCondition) {
        Pageable pageable = PageRequest.of(userCondition.getPage(),userCondition.getSize(),
                Sort.Direction.ASC,"modifiedTime");
        Page<UserEntity> list = userJPA.findAll((Specification<UserEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] p = new Predicate[predicates.size()];
            if(!StringUtils.isEmpty(userCondition.getUsernameKey())){
                String pattern = "%" + (userCondition.getUsernameKey() == null ? "" : userCondition.getUsernameKey())+ "%";
                predicates.add(criteriaBuilder.like(root.get("username"), pattern));
            }
            return criteriaBuilder.and(predicates.toArray(p));
        }, pageable);
        return list;
    }

    @Transactional
    @Override
    public Object modified(UserEntity userEntity) {
        UserEntity user = userJPA.findById(userEntity.getId()).get();
        if(null != userEntity.getStatus()){
            user.setStatus(userEntity.getStatus());
        }
        userJPA.save(user);
        return modifiedSuccess();
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
