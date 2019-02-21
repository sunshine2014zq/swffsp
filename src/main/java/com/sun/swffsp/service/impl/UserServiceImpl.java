package com.sun.swffsp.service.impl;

import com.sun.swffsp.entity.database.UserEntity;
import com.sun.swffsp.jpa.UserJPA;
import com.sun.swffsp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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


}
