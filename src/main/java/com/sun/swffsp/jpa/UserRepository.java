package com.sun.swffsp.jpa;

import com.sun.swffsp.dto.core.UserDto;
import com.sun.swffsp.jpa.base.BaseRepository;

/**
 * user表JPA数据库操作接口
 *
 * @author sun
 * @date 2019/1/8 13:32
 */
public interface UserRepository extends BaseRepository<UserDto> {

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    public UserDto findByUsername(String username);


}
