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


    String create(Flux<String> contextStream) {
        String contextKey = "evalContext_" + UUID.randomUUID().toString()
        redisStreamHandler.handleStream(contextKey, contextStream, context -> {
            // Create StandardEvaluationContext and perform necessary operations
            evaluationContext.put(contextKey, new StandardEvaluationContext(context))
        })
        return contextKey
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