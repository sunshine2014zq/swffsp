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
public interface UserJPA extends JpaRepository<UserEntity,Long>,JpaSpecificationExecutor<UserEntity>,Serializable {

    public UserEntity findByUsername(String username);
}
