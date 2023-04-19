package com.mpc.spellcaster.config

import com.mpc.spellcaster.Context
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    RedisTemplate<String, Context> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Context> template = new RedisTemplate<String, Context>()
        template.setConnectionFactory(connectionFactory)
        template.setKeySerializer(new StringRedisSerializer())
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer())
        return template
    }
}