package com.sun.swffsp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sun.swffsp.dto.condition.UserCondition;
import com.sun.swffsp.dto.db.PrivilegeEntity;
import com.sun.swffsp.dto.db.RoleEntity;
import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.jpa.RoleRepository;
import com.sun.swffsp.jpa.UserRepository;
import com.sun.swffsp.security.CustomGrantedAuthority;
import com.sun.swffsp.service.SecurityService;
import com.sun.swffsp.service.base.BaseService;
import com.sun.swffsp.service.base.PredicateUtils;
import com.sun.swffsp.utils.StringUtils;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 用户业务
 *
 * @author sun
 * @date 2019/1/10 14:20
 */
@Service
public class SecurityServiceImpl extends BaseService<UserEntity>implements SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void initBaseRepository() {
        super.setBaseRepository(userRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("未查找到用户：" + s);
        }
        if (user.getStatus() == UserEntity.STATUS_DELETED) {
            throw new RuntimeException("账号不存在");
        } else if (user.getStatus() == UserEntity.STATUS_INVALID) {
            throw new RuntimeException("账号不可用");
        } else if (user.getStatus() != UserEntity.STATUS_NORMAL) {
            throw new RuntimeException("账号异常");
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


    @Override
    public Object info() {
        JSONObject result = new JSONObject();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        result.put("username", auth.getName());
        //合并所有权限-去重
        List<PrivilegeEntity> privileges = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<CustomGrantedAuthority> authorities = (List<CustomGrantedAuthority>) auth.getAuthorities();
        for (CustomGrantedAuthority authority : authorities) {
            List<PrivilegeEntity> privilegeList = authority.getRole().getPrivileges();
            if (privilegeList != null) {
                for (PrivilegeEntity privilege : privilegeList) {
                    if (!codes.contains(privilege.getCode())) {
                        privileges.add(privilege);
                    }
                }
            }
        }
        //将权限排成树形菜单
        List<JSONObject> menus = menuTree(privileges);
        result.put("menus", menus);
        return result;
    }

    @Override
    public Page<UserEntity> list(UserCondition userCondition) {
        Pageable pageable = PageRequest.of(userCondition.getPage(), userCondition.getSize(),
                Sort.Direction.DESC, "createTime");
        Page<UserEntity> list = userRepository.findAll((Specification<UserEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            //like表达式
            String pattern = "%" + StringUtils.ifNullToEmptyStr(userCondition.getUsernameKey()) + "%";
            PredicateUtils pu = new PredicateUtils(root,criteriaQuery,criteriaBuilder);
            return pu
                    .and(pu.like("username",pattern))
                    .and(pu.notIn("status",UserEntity.STATUS_DELETED))
                    .getPredicate();
        }, pageable);
        return list;
    }

    @Override
    public Object save(UserEntity userEntity) {
        try {
            if(StringUtils.isEmpty(userEntity.getId())) {
                //添加
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String password = encoder.encode(userEntity.getPassword().trim());
                userEntity.setPassword(password);
            }
            return saveNotNull(userEntity);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return responseMap(false,"系统繁忙!请稍后重试");
        }
    }

    @Override
    public Map delete(List<String> ids) {
        return softDelete(ids);
    }

    @Override
    public List<RoleEntity> roles() {
        List<RoleEntity> roles = roleRepository.findAll((Specification<RoleEntity>) (root, query, criteriaBuilder) -> {
            PredicateUtils pu = new PredicateUtils(root,query,criteriaBuilder);
            return pu.equal("status",RoleEntity.STATUS_NORMAL);
        });
        return roles;
    }

    @Override
    protected boolean check(UserEntity entity, Map fieldErr) {
        return false;
    }

    /**
     * 将权限排成树形菜单
     *
     * @param privileges
     * @return
     */
    private List<JSONObject> menuTree(List<PrivilegeEntity> privileges) {
        List<JSONObject> menus = new ArrayList<>();
        Iterator<PrivilegeEntity> iterator = privileges.iterator();
        while (iterator.hasNext()) {
            PrivilegeEntity privilege = iterator.next();
            if (privilege.getType().equals(PrivilegeEntity.TYPE_MENU_1)) {
                //一级菜单
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(privilege));
                //为一级菜单查找子菜单
                List<PrivilegeEntity> subMenus = new ArrayList<>();
                privileges.forEach(p -> {
                    if (privilege.getCode().equals(p.getParentCode())) {
                        subMenus.add(p);
                    }
                });
                object.put("subMenus", subMenus);
                menus.add(object);
                iterator.remove();
//                privileges.removeAll(subMenus);
            }
        }
        return menus;
    }
}
