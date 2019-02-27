package com.sun.swffsp.jpa;

import com.sun.swffsp.dto.db.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * user表JPA数据库操作
 *
 * @author sun
 * @date 2019/1/8 13:32
 */
public interface UserJPA extends JpaRepository<UserEntity,String>,JpaSpecificationExecutor<UserEntity>,Serializable {

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    public UserEntity findByUsername(String username);


}
