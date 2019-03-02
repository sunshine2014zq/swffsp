package com.sun.swffsp.dto.db;

import com.sun.swffsp.dto.db.base.BaseEntity;
import com.sun.swffsp.security.CustomGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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
public class UserEntity extends BaseEntity implements Serializable, UserDetails {

    /**
     * 用户名
     */
    @Column(name = "username", length = 32, nullable = false, unique = true)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", length = 64)
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
    private String nickName;

    /**
     * 邮箱
     */
    @Column(name = "mail", length = 32)
    private String mail;

    /**
     * 手机号
     */
    @Column(name = "phone_num", length = 16)
    private String phoneNum;

    /**
     * qq号
     */
    @Column(name = "qq_num", length = 16)
    private String qqNum;

    /**
     * 微信号
     */
    @Column(name = "wx_num", length = 16)
    private String wxNum;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_code", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_code", referencedColumnName = "code"))
    private List<RoleEntity> roles;

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
        for (RoleEntity role : roles) {
            auths.add(new CustomGrantedAuthority(role));
        }
        return auths;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", nickName='" + nickName + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", qqNum='" + qqNum + '\'' +
                ", wxNum='" + wxNum + '\'' +
                '}';
    }
}
