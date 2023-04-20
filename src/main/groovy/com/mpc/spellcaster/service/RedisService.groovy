package com.mpc.spellcaster.service

import com.mpc.spellcaster.error.ContextNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate

    String saveContext(String key, String context) {
        return reactiveRedisTemplate.opsForValue().set(key, context).map(
                result -> "result: " + result)
    }

    Object getContext(String key) {
        final Object context = reactiveRedisTemplate.opsForValue().get(key)
        if (context == null) {
            throw new ContextNotFoundException("Context not found for key: $key")
        }
        return context
    }

    void deleteContext(String key) {
        redisTemplate.delete(key)
    }
}