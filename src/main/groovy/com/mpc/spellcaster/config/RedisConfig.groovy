package com.mpc.spellcaster.config

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Value("spring.redis.host")
    private String redisHost

    @Value("spring.redis.port")
    private String redisPort

    @Bean
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer()
        RedisSerializer<String> valueSerializer = new StringRedisSerializer()
        RedisSerializationContext<String, String> serializationContext = RedisSerializationContext
                .<String, String>newSerializationContext()
                .key(keySerializer)
                .value(valueSerializer)
                .hashKey(keySerializer)
                .hashValue(valueSerializer)
                .build()
        return new ReactiveRedisTemplate<>(redisConnectionFactory, serializationContext)
    }

    @Bean
     LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory()
    }
//
//    @Bean
//    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
//        RedisSerializer<String> keySerializer = new StringRedisSerializer();
//        RedisSerializer<String> valueSerializer = new StringRedisSerializer();
//        RedisSerializationContext<String, String> serializationContext =
//                RedisSerializationContext.<String, String> newSerializationContext()
//                        .key(keySerializer)
//                        .value(valueSerializer)
//                        .hashKey(keySerializer)
//                        .hashValue(valueSerializer)
//                        .build()
//
//        return new ReactiveRedisTemplate<>(factory, serializationContext);
//    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>()
        template.setConnectionFactory(connectionFactory)
        template.setKeySerializer(new StringRedisSerializer())
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer())
        return template
    }

}