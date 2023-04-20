package com.mpc.spellcaster.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mpc.spellcaster.api.RedisStreamHandler
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


import java.util.concurrent.ConcurrentHashMap

@Service
class ContextService {

    @Autowired
    private RedisService redisService

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate

    @Autowired
    private RedisStreamHandler redisStreamHandler

    private final static JsonSlurper jsonSlurper =
            new JsonSlurper(type: JsonParserType.CHARACTER_SOURCE, lazyChop: false, chop: false)

    private final Map<String, StandardEvaluationContext> evaluationContext = new ConcurrentHashMap<>()


    String create(String contextStream) {

        String contextKey = "evaluationContext_" + UUID.randomUUID().toString()
        // Create a key for the context in Redis
        redisService.saveContext(contextKey, contextStream)
        evaluationContext.put(contextKey, new StandardEvaluationContext(jsonSlurper.parseText(context)))
        // Return a response immediately to the client
        return Mono.just("Context streaming in progress. Context key: " + contextKey).block()
    }



    void update(String key, String context) {
        Object contextMap = jsonSlurper.parseText(context)
        redisService.saveContext(key, contextMap)
        evaluationContext.put(key, new StandardEvaluationContext(contextMap))
    }

    StandardEvaluationContext get(String key) {
        StandardEvaluationContext context = evaluationContext.get(key)
        if (context == null) {
            final Object contextMap = redisService.getContext(key)
            if (contextMap != null) {
                context = new StandardEvaluationContext(contextMap)
                evaluationContext.put(key, context)
            }
        }
        return context
    }

    void delete(String key) {
        redisService.deleteContext(key)
        evaluationContext.remove(key)
    }


}