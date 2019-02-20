package com.sun.swffsp.entity;

import com.sun.swffsp.entity.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class UserEntity extends BaseEntity implements Serializable,UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    /** 用户名 */
    @Column(name="username")
    private String username;

    /** 密码 */
    @Column(name="password")
    private String password;

    /**账号类型 */
    @Column(name="type")
    private Integer type;

    /** 昵称 */
    @Column(name="nick_name")
    private String nickName;

    /** 邮箱 */
    @Column(name="mail")
    private String mail;

    /** 手机号 */
    @Column(name="phone_num")
    private String phoneNum;

    /** qq号 */
    @Column(name="qq_num")
    private String qqNum;

    /** 微信号 */
    @Column(name="wx_num")
    private String wxNum;

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
//        auths.add(new SimpleGrantedAuthority("admin"));
        return auths;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
