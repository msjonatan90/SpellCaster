package com.mpc.spellcaster.service

import com.mpc.spellcaster.error.ContextNotFoundException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService {

    private final RedisTemplate<String, Object> redisTemplate

    RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate
    }

    String saveContext(String appName, String contextKey, Object contextPayload) {
        return redisTemplate.opsForHash().put(appName, contextKey, contextPayload)
    }

    Object getContext(String appName, String contextKey) {
        final Object context = redisTemplate.opsForHash().get(appName, contextKey)
        if (context == null) {
            throw new ContextNotFoundException("Context not found for contextKey: $contextKey")
        }
        return context
    }
    void deleteContext(String appName, String key) {
        redisTemplate.opsForHash().delete(appName, key)
    }
}