package com.mpc.spellcaster.service

import com.mpc.spellcaster.message.ContextUploadedMessage
import com.mpc.spellcaster.model.Context
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import org.springframework.expression.EvaluationContext
import org.springframework.expression.spel.support.SimpleEvaluationContext
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


    String create(String appName, String contextJson) {
        // Parse the context from the request body into an Object
        def contextObject = jsonSlurper.parseText(contextJson)
        // Create a new key for the context
        String contextKey = UUID.randomUUID().toString()

        def context = new Context() //TODO use Context object instead and figure out how to dynamically add properties to it
        contextObject.each { k, v -> context."$k" = v } //TODO was supposed to be a hack to dynamically add properties to the Context object, but fails Property or field 'field1' cannot be found on object of type 'com.mpc.spellcaster.model.Context'

        //Save the context to Redis
        redisService.saveContext(appName, contextKey, contextObject) //TODO use Context object instead

        // Create a new EvaluationContext for the context and add it to the cache
        evaluationContext.put(contextKey, new SpellCasterContext(contextObject)) //TODO use Context object instead

        // Return the contextKey
        return contextKey

    }

    void onUploadComplete(ContextUploadedMessage message) {
        evaluationContext.put(message.contextKey, new SpellCasterContext(message.contextPayload))
    }

    void update(String appName, String contextKey, String contextJson) {
        // Parse the context from the request body into an Object
        def contextObject = jsonSlurper.parseText(contextJson)
        // Save the context to Redis
        redisService.saveContext(appName, contextKey, contextObject)
        // Update the EvaluationContext for the context in the cache
        evaluationContext.put(contextKey, new SpellCasterContext(context))
    }

    EvaluationContext get(String appName, String contextKey) {
        // Get the EvaluationContext from the cache
        EvaluationContext context = evaluationContext.get(contextKey)
        if (context == null) { // If it's not in the cache, get it from Redis
            Object contextObject = redisService.getContext(appName, contextKey)
            // Create a new EvaluationContext for the context and add it to the cache
            evaluationContext.put(contextKey, new SpellCasterContext(contextObject))
        }
        return context
    }

    void delete(String appName, String contextKey) {
        // Delete the context from Redis
        redisService.deleteContext(appName, contextKey)
        // Delete the context from the cache
        evaluationContext.remove(contextKey)
    }
}