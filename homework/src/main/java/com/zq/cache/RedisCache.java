package com.zq.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zq.util.ApplicationContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author zhaoqi
 * @version 1.8
 */
public class RedisCache implements Cache {
//    当前缓存namespace
    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
       RedisTemplate redisTemplate =(RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
//        设置key的序列化方式 默认为jdk序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//          设置value的序列化方式 采用jackson的方式   保证存储成功
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        保证value反序列化成功
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//      利用hash存储到redis中
        redisTemplate.opsForHash().put(id,key.toString(),value);
    }

    @Override
    public Object getObject(Object o) {
        RedisTemplate redisTemplate =(RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate.opsForHash().get(id, o.toString());
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate =(RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        RedisTemplate redisTemplate =(RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return  redisTemplate.opsForHash().size(id).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
