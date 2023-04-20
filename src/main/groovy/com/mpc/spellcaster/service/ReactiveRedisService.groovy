package com.mpc.spellcaster.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReactiveRedisService {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate

    Mono<String> getContext(String key) {
        return reactiveRedisTemplate.opsForValue().get(key)
    }

    Mono<Boolean> saveContext(String key, String context) {
        return reactiveRedisTemplate.opsForValue().set(key, context)
    }

    void deleteContext(String key) {
        reactiveRedisTemplate.delete(key).subscribe()
    }
}
