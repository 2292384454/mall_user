package com.mall.user;

import com.alibaba.fastjson.JSONObject;
import com.mall.user.pojo.ProductInfo;
import com.mall.user.service.ProductInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/23 22:31
 *
 * @author KevinHwang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestConfiguration("classpath:application.yml")
public class RedisTemplateTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ProductInfoService productInfoService;


    @Test
    public void Test1() {
        JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get("prod" + 1L);
        assert jsonObject != null;
        ProductInfo productInfo = jsonObject.toJavaObject(ProductInfo.class);
        System.out.println(productInfo.getIntro());
    }
}
