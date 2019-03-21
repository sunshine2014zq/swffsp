package com.sun.swffsp.dto.core;

import com.sun.swffsp.dto.core.base.BaseDto;
import com.sun.swffsp.security.CustomGrantedAuthority;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户实体
 *
 * @author sun
 * @date 2019/2/20 14:29
 */
@Table(name = "user")
@Entity
@Data
@ToString(exclude = {"roles"})
public class UserDto extends BaseDto implements Serializable, UserDetails {

    /**
     * 用户名
     */
    @Column(name = "username", length = 32, nullable = false)
    @NotBlank(groups = {Add.class})
    @Length(max = 32, message = LENGTH_MAX_MESSAGE, groups = {Add.class, Update.class})
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", length = 64)
    @Length(max = 64, message = LENGTH_MAX_MESSAGE, groups = {Add.class, Update.class})
    private String password;

    /**
     * 账号类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 昵称
     */
    @Column(name = "nick_name", length = 16)
    @Length(max = 16, message = LENGTH_MAX_MESSAGE, groups = {Add.class, Update.class})
    private String nickName;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 32)
    @Length(max = 32, message = LENGTH_MAX_MESSAGE, groups = {Add.class, Update.class})
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone_num", length = 16)
    @Length(max = 16, message = LENGTH_MAX_MESSAGE, groups = {Add.class, Update.class})
    private String phoneNum;

    /**
     * qq号
     */
    @Column(name = "qq_num", length = 16)
    @Length(max = 16, message = LENGTH_MAX_MESSAGE, groups = {Add.class, Update.class})
    private String qqNum;

    /**
     * 微信号
     */
    @Column(name = "wx_num", length = 16)
    @Length(max = 16, message = LENGTH_MAX_MESSAGE, groups = {Add.class, Update.class})
    private String wxNum;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_code", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_code", referencedColumnName = "code"))
    private List<RoleDto> roles;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        for (RoleDto role : roles) {
            auths.add(new CustomGrantedAuthority(role));
        }
        return auths;
    }
}
