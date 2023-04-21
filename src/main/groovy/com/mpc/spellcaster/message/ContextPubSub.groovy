//package com.mpc.spellcaster.message
//
//import com.mpc.spellcaster.service.ContextService
//import groovy.transform.CompileStatic
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.messaging.handler.annotation.MessageMapping
//import org.springframework.stereotype.Component
//import redis.clients.jedis.Jedis
//import groovy.json.JsonOutput
//
//@CompileStatic
//@Component
//class ContextPubSub {
//
//    private final RedisTemplate<String, Object> redisTemplate
//
//    private String contextKey
//
//    ContextPubSub(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate
//        this.contextKey = contextKey
//    }
//
//    @MessageMapping("context-uploaded")
//    void onMessage(String channel, String message) {
//        Object contentPayload = ContextService.jsonSlurper.parseText(message)
//        redisTemplate.opsForHash().put(channel.replaceAll(":file", ""), contextKey, contentPayload)
//
//
//        final ContextUploadedMessage uploadedMessage = new ContextUploadedMessage(
//                contextKey: contextKey,
//                contextPayload: contentPayload)
//
//        Jedis jedis = redisTemplate.getConnectionFactory().getConnection().getNativeConnection()
//
//        jedis.publish("context-uploaded", JsonOutput.toJson(uploadedMessage))
//    }
//
//}
