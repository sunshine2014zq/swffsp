package com.sun.swffsp.dto.admin.result;

import lombok.Data;

import java.util.List;

/**
 * /security/userInfo请求结果data
 * @author sun
 * @date 2019/3/13 12:40
 */
@Data
public class UserInfoResult {

    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 菜单
     */
    private List<Menu> menus;

    @Data
    public static class Menu {
        /**
         * 菜单名称
         */
        private String name;
        /**
         * 菜单地址
         */
        private String url;
        /**
         * 子菜单
         */
        private List<Menu> subMenus;
    }
}
