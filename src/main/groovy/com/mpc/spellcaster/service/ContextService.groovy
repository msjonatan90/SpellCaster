package com.mpc.spellcaster.service

import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentHashMap

@Service
class ContextService {

    private final RedisService redisService

    private final static JsonSlurper jsonSlurper =
            new JsonSlurper(type: JsonParserType.CHARACTER_SOURCE, lazyChop: false, chop: false)

    private final Map<String, StandardEvaluationContext> evaluationContext = new ConcurrentHashMap<>()

    ContextService(RedisService redisService){
        this.redisService = redisService
    }

    String create(String appName, String contextStream) {
        // Create a key for the context in Redis
        String contextKey = UUID.randomUUID().toString()
        redisService.saveContext(appName, contextKey, contextStream)
        evaluationContext.put(contextKey, new StandardEvaluationContext(jsonSlurper.parseText(contextStream)))
        // Return a response immediately to the client
        return contextKey
    }

    void update(String appName, String key, String context) {
        Object contextMap = jsonSlurper.parseText(context)
        redisService.saveContext(appName, key, contextStream)
        evaluationContext.put(key, new StandardEvaluationContext(contextMap))
    }

    StandardEvaluationContext get(String appName, String key) {
        StandardEvaluationContext context = evaluationContext.get(key)
        if (context == null) {
            Object contextMap = redisService.getContext(appName, key)
            context = new StandardEvaluationContext(contextMap)
            evaluationContext.put(key, context)
        }
        return context
    }

    void delete(String appName, String key) {
        redisService.deleteContext(appName, key)
        evaluationContext.remove(key)
    }

}