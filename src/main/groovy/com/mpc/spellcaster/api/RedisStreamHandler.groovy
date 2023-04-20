package com.mpc.spellcaster.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

import java.util.function.Consumer

@Component
class RedisStreamHandler {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate

    void handleStream(String contextKey, Flux<String> contextStream, Consumer<String> consumer) {
                contextStream
                .flatMap { reactiveRedisTemplate.opsForStream()
                        .add(contextKey, ["createContext", it]).subscribe(consumer) }
        .subscribe()
    }
}
