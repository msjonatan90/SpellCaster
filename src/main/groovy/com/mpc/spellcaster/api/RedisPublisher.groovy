package com.mpc.spellcaster.api

import reactor.core.publisher.Flux
import org.springframework.data.redis.core.ReactiveRedisTemplate

import java.util.function.Consumer

class RedisPublisher {

    private final ReactiveRedisTemplate<String, String> redisTemplate

    RedisPublisher(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate
    }

    void handleStream(Flux<String> contextStream, Consumer<String> consumer) {
        String uuid = UUID.randomUUID().toString()
        contextStream.subscribe(consumer)
        contextStream.map(context -> Tuple.of(uuid, context))
                .flatMap(tuple -> redisTemplate.opsForStream()
                        .add(tuple.getT1(), Collections.singletonMap("context", tuple.getT2())))
                .subscribe()
    }
}
