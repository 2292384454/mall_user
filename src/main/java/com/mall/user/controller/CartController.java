package com.mall.user.controller;

import com.mall.user.pojo.OtherConfig;
import com.mall.user.pojo.UserInfo;
import com.mall.user.repository.CartRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/22 15:18
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/cart")
@Log4j2
public class CartController {
    // 购物车容量
    private final int CAPACITY;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CartRepository cartRepository;

    @Autowired
    public CartController(OtherConfig otherConfig, RedisTemplate<String, Object> redisTemplate, CartRepository cartRepository) {
        this.CAPACITY = otherConfig.getCartCapacity();
        this.redisTemplate = redisTemplate;
        this.cartRepository = cartRepository;
    }


    /**
     * 获取整个购物车
     */
    @RequestMapping
    @ResponseBody
    public List<Map<String, Long>> getCart(Authentication authentication, HttpServletResponse httpServletResponse) {
        if (authentication != null) {
            UserInfo user = (UserInfo) authentication.getPrincipal();
            Long userId = user.getId();
            System.out.println(userId + "获取购物车");
            String cartKey = "cart:" + userId;
            // 如果购物车存在
            if (Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
                HashOperations<String, String, Integer> hashOps = redisTemplate.opsForHash();
                // 获取购物车信息
                Map<String, Integer> cart = hashOps.entries(cartKey);
                List<Map<String, Long>> response = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    Map<String, Long> temp = new HashMap<>();
                    temp.put("id", Long.valueOf(entry.getKey()));
                    temp.put("count", Long.valueOf(entry.getValue()));
                    response.add(temp);
                }
                return response;
            } else {
                httpServletResponse.setStatus(404);
                return null;
            }
        }
        return null;
    }

    /**
     * 添加商品到购物车
     */
    @RequestMapping(value = "/additem")
    @ResponseBody
    public String addItem(Authentication authentication, @RequestParam("itemId") Long itemId) {
        if (authentication != null) {
            UserInfo user = (UserInfo) authentication.getPrincipal();
            Long userId = user.getId();
            String cartKey = "cart:" + userId;
            if (cartRepository.addItem(cartKey, itemId)) {
                log.info("用户{}添加商品{}到购物车", userId, itemId);
                return "sucess";
            } else {
                return "添加商品失败，购物车已满！";
            }
        } else {
            return "请先登录";
        }
    }

    /**
     * 修改购物车中商品数量
     */
    @RequestMapping(value = "/handleCount")
    @ResponseBody
    public String handleCount(Authentication authentication, @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        UserInfo user = (UserInfo) authentication.getPrincipal();
        Long userId = user.getId();
        String cartKey = "cart:" + userId;
        HashOperations<String, String, Integer> hashOps = redisTemplate.opsForHash();
        if (count < 0 && hashOps.get(cartKey, String.valueOf(itemId)) < 1 - count) {
            return "";
        }
        hashOps.increment(cartKey, String.valueOf(itemId), count);
        log.info("用户{}将购物车中商品{}数量增加" + count, userId, itemId);
        return "success";
    }

    /**
     * 修改购物车中商品数量
     */
    @RequestMapping(value = "/delItem")
    @ResponseBody
    public String handleCount(Authentication authentication, @RequestParam("itemId") Long itemId) {
        UserInfo user = (UserInfo) authentication.getPrincipal();
        Long userId = user.getId();
        String cartKey = "cart:" + userId;
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.delete(cartKey, String.valueOf(itemId));
        log.info("用户{}从购物车中删除商品{}", userId, itemId);
        return "success";
    }
}
