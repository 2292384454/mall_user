package com.mall.user.security;

import com.mall.user.pojo.UserInfo;
import com.mall.user.service.impl.UserInfoServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security配置类.
 * <p>
 * 创建时间: 2021/6/11 19:30
 *
 * @author KevinHwang
 */
@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserInfoServiceImpl userInfoService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Autowired
    public SecurityConfig(UserInfoServiceImpl userInfoService, LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler) {
        this.userInfoService = userInfoService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义用户认证
        auth.userDetailsService(userInfoService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()// 对请求授权
                // 所有请求都需要认证
                .antMatchers("/login").permitAll()
                .antMatchers("/product/**").permitAll()
                .antMatchers("/order/**").authenticated()

                .and()
                .formLogin()
                // 指定登录页视图
                .loginPage("/login")
                // 指定登录成功行为
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                // 必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                .permitAll()

                .and()
                .logout()
                // 指定退出成功行为
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    UserInfo user = (UserInfo) authentication.getPrincipal();
                    // 退出成功日志记录
                    log.info("退出成功，{id:" + user.getId() + ",用户名:" + user.getUsername() + "}");
                    // 退出成功重定向到主页
                    httpServletResponse.sendRedirect("/");
                })

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                //TODO:关掉CSRF验证(防止跨站请求伪造攻击)进行测试，正式上线时注释掉
                .and()
                .csrf()
                .disable();

    }
}
