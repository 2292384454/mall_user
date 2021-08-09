package com.mall.user.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * SpringBoot 自动配置的这个 RedisTemplate 是没有设置数据读取时的 key 及 value 的序列化方式的。
 * 所以，我们要写一个自己的 RedisTemplate 并设置 key 及 value 的序列化方式才可以正常操作 Redis.
 * <p>
 * 创建时间: 2021/6/23 22:14
 *
 * @author KevinHwang
 */
@Configuration
public class RedisConfig {
    @Bean
    @SuppressWarnings("all") //告诉编译器忽略全部的警告，不用在编译完成后出现警告信息
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // fastJSON 序列化示例
        RedisSerializer<Object> jsonString = new FastJsonRedisSerializer<>(Object.class);
        // String 的 key 和 hash 的 key 都采用 String 的序列化方式
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        // value 都采用 fastjson 的序列化方式
        redisTemplate.setValueSerializer(jsonString);
        redisTemplate.setHashValueSerializer(jsonString);
        // 开启 redis 事务支持
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
}
