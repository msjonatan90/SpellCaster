//package com.mpc.spellcaster.message
//
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.stereotype.Component
//
//@Component
//class ContextPublisher {
//
//    private final RedisTemplate<String, String> redisTemplate
//
//    ContextPublisher(RedisTemplate redisTemplate){
//        this.redisTemplate = redisTemplate
//    }
//
//    void publish(String message) {
//        redisTemplate.convertAndSend("update-context", message)
//    }
//}
