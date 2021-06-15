package com.mall.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.user.pojo.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后，记录登录日志，然后把认证之后的用户 authentication 返给前端.
 * <p>
 * 创建时间: 2021/6/11 19:45
 *
 * @author KevinHwang
 */
@Log4j2
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    @Autowired
    public LoginSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        UserInfo user = (UserInfo) authentication.getPrincipal();
        // 登录成功日志记录
        log.info("登录成功，{id:" + user.getId() + ",用户名:" + user.getUsername() + "}");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 把authentication对象转成 json 格式 字符串 通过 response 以application/json;charset=UTF-8 格式写到响应里面去
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}
