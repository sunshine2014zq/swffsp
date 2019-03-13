package com.sun.swffsp.dto.db;

import com.sun.swffsp.dto.db.base.BaseEntity;
import lombok.Data;

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
@Data
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

    @Override
    public String toString() {
        return "RoleEntity{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
