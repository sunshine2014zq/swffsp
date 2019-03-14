package com.sun.swffsp.dto.admin.query;

import com.sun.swffsp.dto.admin.query.base.PageCondition;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户模块条件查询
 *
 * @author sun
 * @date 2019/3/14 17:32
 */
@Data
public class UserCondition extends PageCondition implements Serializable {

    /**
     * 用户名关键字
     */
    private String usernameKey;

}
