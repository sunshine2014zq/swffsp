package com.sun.swffsp.entity.database;

import com.sun.swffsp.entity.database.base.BaseEntity;

import javax.persistence.*;

/**
 * 角色实体
 *
 * @author sun
 * @date 2019/2/21 12:16
 */
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    /**
     * 角色代码
     */
    @Column(name = "code", length = 32, nullable = false, unique = true)
    private String code;
    /**
     * 角色名称
     */
    @Column(name = "name", length = 16, nullable = false)
    private String name;

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

    @Override
    public String toString() {
        return "RoleEntity{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
