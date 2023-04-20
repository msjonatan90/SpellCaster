//package com.mpc.spellcaster.message
//
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.stereotype.Component
//
//@Component
//class ContextSubscriber {
//
//    private final RedisTemplate<String, Object> redisTemplate
//
//    ContextSubscriber(RedisTemplate redisTemplate){
//        this.redisTemplate = redisTemplate
//    }
//
//    void handleMessage(String message) {
//        // handle the message here
//        print(message)
//    }
//}