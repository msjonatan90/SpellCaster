package com.mpc.spellcaster.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RedisProperties {

    private final int redisPort
    private final String redisHost

    RedisProperties(
            @Value('${spring.redis.port}') int redisPort,
            @Value('${spring.redis.host}') String redisHost) {
        this.redisPort = redisPort
        this.redisHost = redisHost
    }

    int getRedisPort() {
        return redisPort
    }

    String getRedisHost() {
        return redisHost
    }
}
