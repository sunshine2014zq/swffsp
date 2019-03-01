package com.sun.swffsp.jpa;

import com.sun.swffsp.dto.db.UserEntity;
import com.sun.swffsp.jpa.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * user表JPA数据库操作接口
 *
 * @author sun
 * @date 2019/1/8 13:32
 */
public interface UserRepository extends BaseRepository<UserEntity> {

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    public UserEntity findByUsername(String username);


}
