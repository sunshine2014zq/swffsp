package com.sun.swffsp.dto.req;

import com.sun.swffsp.dto.req.base.PageCondition;

/**
 * 用户查询条件
 *
 * @author sun
 * @date 2019/2/26 10:22
 */
public class UserCondition extends PageCondition {

    /**
     * 用户名查找关键字
     */
    private String usernameKey;

    public String getUsernameKey() {
        return usernameKey;
    }

    public void setUsernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
    }
}
