package com.mpc.spellcaster.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    private final RedisProperties redisProperties

    RedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisProperties.redisHost, redisProperties.redisPort
        )
        redisStandaloneConfiguration.setL
        return new JedisConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>()
        template.setConnectionFactory(redisConnectionFactory)
        template.setKeySerializer(new StringRedisSerializer())
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer())
        template.setHashKeySerializer(new StringRedisSerializer())
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer())
        template.afterPropertiesSet()
        return template
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory){

        RedisMessageListenerContainer container = new RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        return container
    }
}