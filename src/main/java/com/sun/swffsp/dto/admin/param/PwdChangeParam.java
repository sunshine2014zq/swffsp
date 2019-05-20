package com.sun.swffsp.dto.admin.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码参数
 *
 * @author sun
 * @date 2019/3/26 10:37
 */
@Data
public class PwdChangeParam implements Serializable {

    /**
     * 旧密码
     */
    private String oldPwd;
    /**
     * 新密码
     */
    private String newPwd;
}
