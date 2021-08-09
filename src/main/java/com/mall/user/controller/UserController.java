package com.mall.user.controller;

import com.mall.user.pojo.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/23 23:28
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping
    @ResponseBody
    public UserInfo getUser(Authentication authentication) {
        if (authentication != null) {
            return (UserInfo) authentication.getPrincipal();
        } else {
            return null;
        }
    }
}
