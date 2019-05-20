package com.sun.swffsp.dto.admin.param;

import com.sun.swffsp.dto.admin.param.base.PageQuery;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户模块条件查询
 *
 * @author sun
 * @date 2019/3/14 17:32
 */
@Data
public class UserQuery extends PageQuery implements Serializable {

    /**
     * 用户名关键字
     */
    private String usernameKey;

}
