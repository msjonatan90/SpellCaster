package com.mpc.spellcaster.service

import org.springframework.expression.EvaluationContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component

@Component
class SpellCasterService {

    static final SpelExpressionParser eval = new SpelExpressionParser()

    private final ContextService contextService
    private final RedisService redisService

    SpellCasterService(ContextService contextService, RedisService redisService) {
        this.contextService = contextService
        this.redisService = redisService
    }

    Object evaluate(String appName, String contextKey, String expression) {
        EvaluationContext evaluationContext = contextService.get(appName, contextKey)
        final Object result = eval.parseExpression(expression)
                .getValue(evaluationContext)
        //TODO since the eval.parseExpression could have modified the context, we need to save it back to Redis using the Redis Service

        return result
    }

    Object evaluateOnRedis(String appName, String contextKey, String expression){
        return redisService.findByExpression(appName, contextKey, expression)
    }

    //TODO this isn't working
    static boolean validate(String expression) {
        //TODO Validates the integrity and format of the SpEL expression
        return false //TODO return the result of the validation
    }

}