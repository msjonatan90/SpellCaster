package com.mpc.spellcaster.service

import com.fasterxml.jackson.databind.ObjectMapper

import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentHashMap

@Service
class ContextService {

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private ReactiveRedisService reactiveRedisService

    private final static JsonSlurper jsonSlurper =
            new JsonSlurper(type: JsonParserType.CHARACTER_SOURCE, lazyChop: false, chop: false)

    private final Map<String, StandardEvaluationContext> evaluationContext = new ConcurrentHashMap<>()


    String create(String contextStream) {

        String contextKey = "evaluationContext_" + UUID.randomUUID().toString()
        // Create a key for the context in Redis
        reactiveRedisService.saveContext(contextKey, contextStream).subscribe { () -> {
            evaluationContext.put(contextKey, new StandardEvaluationContext(jsonSlurper.parseText(contextStream)))
        }}

        // Return a response immediately to the client
        return contextKey
    }



    void update(String key, String context) {
        Object contextMap = jsonSlurper.parseText(context)
        reactiveRedisService.saveContext(key, contextStream).subscribe()
        evaluationContext.put(key, new StandardEvaluationContext(contextMap))
    }

    StandardEvaluationContext get(String key) {
        StandardEvaluationContext context = evaluationContext.get(key)
        if (context == null) {
            String contextJson = reactiveRedisService.getContext(key).block()
            Object contextMap = jsonSlurper.parseText(contextJson)
            context = new StandardEvaluationContext(contextMap)
            evaluationContext.put(key, context)
        }
        return context
    }

    void delete(String key) {
        reactiveRedisService.deleteContext(key).subscribe().block()
        evaluationContext.remove(key)
    }


}