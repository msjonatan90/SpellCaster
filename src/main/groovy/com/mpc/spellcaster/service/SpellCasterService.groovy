package com.mpc.spellcaster.service

import org.springframework.expression.EvaluationContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component

@Component
class SpellCasterService {

    static final SpelExpressionParser eval = new SpelExpressionParser()

    private final ContextService contextService

    SpellCasterService(ContextService contextService) {
        this.contextService = contextService
    }

    Object evaluate(String appName, String contextKey, String expression) {
        EvaluationContext evaluationContext = contextService.get(appName, contextKey)
        final Object result = eval.parseExpression(expression)
                .getValue(evaluationContext)
        //TODO since the eval could have modified the context, we need to save it back to Redis
        return result
    }

    //TODO this isn't working
    static boolean validate(String expression) {
        // Validates the SpEL expression using the context object
        final SpelExpressionParser parser = new SpelExpressionParser()
        return parser.parseRaw(expression).compileExpression()
    }

}