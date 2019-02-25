package com.sun.swffsp.dto.db;

import com.sun.swffsp.dto.db.common.CommonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限实体
 *
 * @author sun
 * @date 2019/2/21 12:36
 */
@Table(name = "privilege")
@Entity
public class PrivilegeEntity extends CommonEntity implements Serializable {

    /**
     * 权限代码
     */
    @Column(name = "code", length = 32, nullable = false, unique = true)
    private String code;

    /**
     * 权限名称
     */
    @Column(name = "name", length = 16, nullable = false)
    private String name;

    /**
     * 权限类型
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 一级菜单
     */
    public static final Integer TYPE_MENU_1 = 1;
    /**
     * 二级菜单
     */
    public static final Integer TYPE_MENU_2 = 2;

    /**
     * 权限地址
     */
    @Column(name = "url", length = 64)
    private String url;

    /**
     * 父权限代码
     */
    @Column(name = "parent_code", length = 32)
    private String parentCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String toString() {
        return "PrivilegeEntity{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", parentCode='" + parentCode + '\'' +
                '}';
    }
}
