package com.mpc.spellcaster.service

import com.mpc.spellcaster.cache.ContextUploadService
import com.mpc.spellcaster.message.ContextUploadedMessage
import com.mpc.spellcaster.model.Context
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import org.springframework.context.expression.MapAccessor
import org.springframework.expression.EvaluationContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.SimpleEvaluationContext
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentHashMap

@Service
class ContextService {

    private final RedisService redisService

    public final static JsonSlurper jsonSlurper =
            new JsonSlurper(type: JsonParserType.CHARACTER_SOURCE, lazyChop: false, chop: false)

    private final Map<String, EvaluationContext> evaluationContext = new ConcurrentHashMap<>()

    ContextService(RedisService redisService) {
        this.redisService = redisService
    }

    String create(String appName, String contextStream) {

        def contextMap = jsonSlurper.parseText(contextStream)
        // Create a key for the context in Redis
        String contextKey = UUID.randomUUID().toString()
        def context = new Context()
        contextMap.each { k, v -> context."$k" = v }
        redisService.saveContext(appName, contextKey, context)
        evaluationContext.put(contextKey, new SpellCasterContext(context))
        // Return a response immediately to the client
        return contextKey

    }
    void onUploadComplete(ContextUploadedMessage message) {
        evaluationContext.put(message.contextKey, SimpleEvaluationContext.forReadWriteDataBinding()
                .withRootObject(message.contextPayload).build())
    }

    void onEvaluationComplete(String appName, String contextKey, Object contextObject) {
        redisService.saveContext(appName, contextKey, contextObject)
    }

    void update(String appName, String key, String context) {
        Object contextObject = jsonSlurper.parseText(context)
        redisService.saveContext(appName, key, contextStream)
        evaluationContext.put(key, SimpleEvaluationContext.forReadWriteDataBinding()
                .withRootObject(contextObject).build())
    }

    EvaluationContext get(String appName, String key) {
        EvaluationContext context = evaluationContext.get(key)
        if (context == null) {
            Object contextObject = redisService.getContext(appName, key)
            context = SimpleEvaluationContext.forReadWriteDataBinding()
                    .withRootObject(contextObject).build()
            evaluationContext.put(key, context)
        }
        return context
    }

    void delete(String appName, String key) {
        redisService.deleteContext(appName, key)
        evaluationContext.remove(key)
    }
//
//    Object eval(String appName, String key, String expression) {
//        final StandardEvaluationContext context = this.get(appName, key)
//        def result = SpellCaster.evaluate(expression, context)
//        redisService.saveContext(appName, key, context.getRootObject())
//        return result
//    }
}