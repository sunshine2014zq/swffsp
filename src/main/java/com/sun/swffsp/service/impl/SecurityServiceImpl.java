package com.sun.swffsp.service.impl;

import com.google.gson.Gson;
import com.sun.swffsp.dto.admin.query.UserCondition;
import com.sun.swffsp.dto.admin.result.FieldErrorsResult;
import com.sun.swffsp.dto.admin.result.UserInfoResult;
import com.sun.swffsp.dto.core.PrivilegeDto;
import com.sun.swffsp.dto.core.RoleDto;
import com.sun.swffsp.dto.core.UserDto;
import com.sun.swffsp.dto.admin.result.base.Response;
import com.sun.swffsp.jpa.RoleRepository;
import com.sun.swffsp.jpa.UserRepository;
import com.sun.swffsp.security.CustomGrantedAuthority;
import com.sun.swffsp.service.SecurityService;
import com.sun.swffsp.service.base.BaseService;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 用户业务
 *
 * @author sun
 * @date 2019/1/10 14:20
 */
@Service
public class SecurityServiceImpl extends BaseService<UserDto> implements SecurityService {

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
        UserDto user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("未查找到用户：" + s);
        }
        if (user.getStatus() == UserDto.STATUS_DELETED) {
            throw new RuntimeException("账号不存在");
        } else if (user.getStatus() == UserDto.STATUS_INVALID) {
            throw new RuntimeException("账号不可用");
        } else if (user.getStatus() != UserDto.STATUS_NORMAL) {
            throw new RuntimeException("账号异常");
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


    @Override
    public UserInfoResult info() {
        UserInfoResult info = new UserInfoResult();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        info.setUsername(auth.getName());
        UserDto user = userRepository.findByUsername(auth.getName());
        info.setNickName(user.getNickName());
        //合并所有权限-去重
        List<PrivilegeDto> privileges = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<CustomGrantedAuthority> authorities = (List<CustomGrantedAuthority>) auth.getAuthorities();
        for (CustomGrantedAuthority authority : authorities) {
            List<PrivilegeDto> privilegeList = authority.getRole().getPrivileges();
            if (privilegeList != null) {
                for (PrivilegeDto privilege : privilegeList) {
                    if (!codes.contains(privilege.getCode())) {
                        privileges.add(privilege);
                    }
                }
            }
        }
        //将权限排成树形菜单
        List<UserInfoResult.Menu> menus = menuTree(privileges);
        info.setMenus(menus);
        return info;
    }

    @Override
    public Page<UserDto> list(UserCondition condition) {
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(),
                Sort.Direction.DESC, "createdTime");
        Page<UserDto> list = userRepository.findAll((Specification<UserDto>) (root, criteriaQuery, criteriaBuilder) -> {
            //like表达式
            String pattern = "%" + StringUtils.ifNullToEmptyStr(condition.getUsernameKey()) + "%";
            return criteriaBuilder.and(criteriaBuilder.like(root.get("username"), pattern),
                    criteriaBuilder.not(root.get("status").in(UserDto.STATUS_DELETED)));
        }, pageable);
        return list;
    }

    @Override
    public Response saveUser(UserDto user) {
        try {
            //用户名重复检测
            List<UserDto> users = userRepository.findAll(uniqueSpecification(user));
            if (users != null && !users.isEmpty()) {
                String msg = user.getUsername() + "已存在";
                return Response.fail("保存失败").data(new FieldErrorsResult[]
                        {new FieldErrorsResult("username", msg)});
            }
            if (StringUtils.isEmpty(user.getId())) {
                //添加
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String password = encoder.encode(user.getPassword().trim());
                user.setPassword(password);
            }
            return validateAndSaveNotNull(user);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return Response.fail("服务繁忙!请稍后重试");
        }
    }

    /**
     * 用户名唯一性校验的Specification
     *
     * @param user
     * @return
     */
    private Specification<UserDto> uniqueSpecification(UserDto user) {
        return (Specification<UserDto>) (root, query, criteriaBuilder) -> {

            Predicate p = criteriaBuilder.and(criteriaBuilder.notEqual(root.get("status"), UserDto.STATUS_DELETED),
                    criteriaBuilder.equal(root.get("username"), user.getUsername()));
            if (!StringUtils.isEmpty(user.getId())) {
                return criteriaBuilder.and(p, criteriaBuilder.notEqual(root.get("id"), user.getId()));
            }
            return p;
        };
    }

    @Override
    public Response delete(List<String> ids) {
        return softDelete(ids);
    }

    @Override
    public List<RoleDto> roles() {
        List<RoleDto> roles = roleRepository.findAll((Specification<RoleDto>)
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("status"), RoleDto.STATUS_NORMAL));
        return roles;
    }

    /**
     * 将权限排成树形菜单
     *
     * @param privileges
     * @return
     */
    private List<UserInfoResult.Menu> menuTree(List<PrivilegeDto> privileges) {
        List<UserInfoResult.Menu> menus = new ArrayList<>();
        Iterator<PrivilegeDto> iterator = privileges.iterator();
        Gson gson = new Gson();
        while (iterator.hasNext()) {
            PrivilegeDto privilege = iterator.next();
            if (privilege.getType().equals(PrivilegeDto.TYPE_MENU_1)) {
                //一级菜单
                UserInfoResult.Menu menu = gson.fromJson(gson.toJson(privilege), UserInfoResult.Menu.class);
                //为一级菜单查找子菜单
                List<UserInfoResult.Menu> subMenus = new ArrayList<>();
                privileges.forEach(p -> {
                    if (privilege.getCode().equals(p.getParentCode())) {
                        subMenus.add(gson.fromJson(gson.toJson(p), UserInfoResult.Menu.class));
                    }
                });
                menu.setSubMenus(subMenus);
                menus.add(menu);
                iterator.remove();
//                privileges.removeAll(subMenus);
            }
        }
        return menus;
    }
}
