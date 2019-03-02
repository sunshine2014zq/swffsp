package com.sun.swffsp.dto.db;

import com.sun.swffsp.dto.db.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色实体
 *
 * @author sun
 * @date 2019/2/21 12:16
 */
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity implements Serializable {

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

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_code", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "privilege_code", referencedColumnName = "code"))
    private List<PrivilegeEntity> privileges;

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

    public List<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeEntity> privileges) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
