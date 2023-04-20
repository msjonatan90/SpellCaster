package com.mpc.spellcaster.service

import com.mpc.spellcaster.error.ContextNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RedisService {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate

    String saveContext(String key, Flux<String> contextStream) {

        // Append each chunk of the context to Redis as it comes in
        Mono<Void> writeContext = contextStream
                .flatMap(chunk -> reactiveRedisTemplate.opsForValue().append(key, chunk))
                .then()
        return key
    }

    Object getContext(String key) {
        final Object context = redisTemplate.opsForValue().get(key)
        if (context == null) {
            throw new ContextNotFoundException("Context not found for key: $key")
        }
        return context
    }

    void deleteContext(String key) {
        redisTemplate.delete(key)
    }
}