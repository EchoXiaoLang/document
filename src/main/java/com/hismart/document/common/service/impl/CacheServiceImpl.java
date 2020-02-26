package com.hismart.document.common.service.impl;


import com.hismart.document.common.service.CacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {


    private final RedisTemplate redisTemplate;


    public CacheServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void testConnect() throws Exception {
        redisTemplate.opsForValue().get("test");
    }

    @Override
    public boolean hasKey(Object key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public <T> T get(Object key) {
        if (key == null) {
            return null;
        }

        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(Object key, Object hashKey) {
        if (key == null) {
            return null;
        }

        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(Object key, Object value, long timeoutSecond) {
        redisTemplate.opsForValue().set(key, value, timeoutSecond, TimeUnit.SECONDS);
    }

    @Override
    public void put(Object key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void expire(Object key, int timeoutSecond) {
        redisTemplate.expire(key, timeoutSecond, TimeUnit.SECONDS);
    }

    @Override
    public <T> T remove(Object key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            redisTemplate.delete(key);
        }

        return (T) value;
    }

    @Override
    public void removeNoReturn(Object key) {
        redisTemplate.delete(key);
    }

    @Override
    public long counterIncrement(Object key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public long getCounter(Object key) {
        return redisTemplate.opsForValue().increment(key, 0);
    }

    @Override
    public void set(Object key, Object value1, long value2, TimeUnit timeoutSecond) {
        redisTemplate.opsForValue().set(key, value1, value2, TimeUnit.SECONDS);
    }



}
