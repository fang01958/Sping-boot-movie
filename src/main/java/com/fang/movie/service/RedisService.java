package com.fang.movie.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    public Boolean cacheSeatInfo(int sceneId, int row ,int col){
        String key = "movice:ticket:" + sceneId + ":" + row + ":" + col;
        return redisTemplate.opsForValue().setIfAbsent(key,"", 30,TimeUnit.SECONDS);
    }

}
