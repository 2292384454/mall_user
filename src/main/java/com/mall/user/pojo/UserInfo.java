package com.mall.user.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * 封装客户信息.
 * <p>
 * 创建时间: 2021/5/28 16:23
 *
 * @author KevinHwang
 */

@Getter
@Setter
@Entity
@Table(name = "user_info")//表明Order实体应该持久化到数据库中指定名字的表中
public class UserInfo implements UserDetails, CredentialsContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "UserName name is required")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password name is required")
    private String password;

    @NotBlank(message = "Real Name name is required")
    @Column(name = "real_name")
    private String realName;

    @NotBlank(message = "Sex name is required")
    private String sex;

    @NotBlank(message = "Address name is required")
    private String address;

    @NotBlank(message = "Phone Number name is required")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "reg_date")
    private String regDate;

    @NotBlank(message = "UserName name is required")
    private int status;

    /**
     * 设置注册时间
     */
    @PrePersist
    void regDate() {
        this.regDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Override
    public String toString() {
        return username;
    }

    /**
     * 获取用户权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 账户是否未过期，过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁，锁定的用户无法进行身份验证.
     * <p>
     * 密码锁定.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码)，过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否被启用或禁用。禁用的用户无法进行身份验证。
     */
    @Override
    public boolean isEnabled() {
        return status != 0;
    }

    /**
     * 认证完成后，擦除密码，用户认证成功后，擦除密码，然后返给前端，防止密码泄露。
     */
    @Override
    public void eraseCredentials() {
        setPassword(null);
    }
}