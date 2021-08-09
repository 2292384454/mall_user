package com.mall.user.repository;

import com.mall.user.pojo.OtherConfig;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/7/8 16:36
 *
 * @author KevinHwang
 */
@Repository
public class CartRepository {
    /**
     * 购物车容量
     */
    private final int CAPACITY;
    private final RedisTemplate<String, Object> redisTemplate;

    public CartRepository(OtherConfig otherConfig, RedisTemplate<String, Object> redisTemplate) {
        CAPACITY = otherConfig.getCartCapacity();
        this.redisTemplate = redisTemplate;
    }

    /**
     * 将 itemId 对应的商品添加到用户 cartKey 对应的的购物车中.
     */
    public boolean addItem(String cartKey, long itemId) {
        // 如果购物车存在
        if (Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            Map<String, String> cart = hashOps.entries(cartKey);
            // 如果用户购物车满了
            if (!cart.containsKey(String.valueOf(itemId)) && cart.size() >= CAPACITY) {
                return false;
            }
        }
        // 向 redis 购物车中加入元素
        // 购物车是以商品id为 key ，商品选购数量为 value 的HASH结构
        redisTemplate.opsForHash().increment(cartKey, String.valueOf(itemId), 1);
        return true;
    }
}
