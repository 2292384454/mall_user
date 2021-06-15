package com.mall.user.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败后根据不同的AuthenticationException返回登录失败的原因给前端.
 * <p>
 * 创建时间: 2021/6/11 19:53
 *
 * @author KevinHwang
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        if (e instanceof AccountExpiredException) {
            //账号过期
            httpServletResponse.getWriter().write("账号过期");
        } else if (e instanceof BadCredentialsException) {
            // 用户名或密码错误
            httpServletResponse.getWriter().write("用户名或密码错误");
        } else if (e instanceof CredentialsExpiredException) {
            // 密码过期
            httpServletResponse.getWriter().write("密码过期");
        } else if (e instanceof DisabledException) {
            // 用户被禁用
            httpServletResponse.getWriter().write("用户被禁用");
        } else if (e instanceof LockedException) {
            // 用户被锁定
            httpServletResponse.getWriter().write("用户被锁定");
        } else if (e instanceof InternalAuthenticationServiceException) {
            // 内部错误
            httpServletResponse.getWriter().write("内部错误");
        } else {
            // 其他错误
            httpServletResponse.getWriter().write("登陆失败，发生未知错误");
        }
    }
}

