package com.zq.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author zhaoqi
 * @version 1.8
 */
@RestController
@RequestMapping("redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("select")
    public Set<ZSetOperations.TypedTuple<String>> select(){
        Set<ZSetOperations.TypedTuple<String>> keyword = stringRedisTemplate.opsForZSet().reverseRangeWithScores("keyword", 0, -1);
        return keyword;
    }
}
