package com.mpc.spellcaster.service

import com.mpc.spellcaster.model.Context
import org.springframework.expression.EvaluationContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component

@Component
class SpellCaster {

    static final SpelExpressionParser eval = new SpelExpressionParser()

    private final ContextService contextService

    SpellCaster(ContextService contextService) {
        this.contextService = contextService
    }

    Object evaluate(String appName, String contextKey, String expression) {
        EvaluationContext evaluationContext = contextService.get(appName, contextKey)
        final Object result = eval.parseExpression(expression)
                .getValue(evaluationContext)
//        contextService.onEvaluationComplete(appName, contextKey, context.getRootObject())
        return result
    }

    static boolean validate(String expression) {
        // Validates the SpEL expression using the context object
        final SpelExpressionParser parser = new SpelExpressionParser()
        return parser.parseRaw(expression).compileExpression()
    }

}