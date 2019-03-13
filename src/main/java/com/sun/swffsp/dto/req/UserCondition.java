package com.sun.swffsp.dto.req;

import com.sun.swffsp.dto.req.base.PageCondition;
import lombok.Data;

/**
 * 用户查询条件
 *
 * @author sun
 * @date 2019/2/26 10:22
 */
@Data
public class UserCondition extends PageCondition {

    /**
     * 用户名查找关键字
     */
    private String usernameKey;

}
