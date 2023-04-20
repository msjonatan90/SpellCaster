package com.mpc.spellcaster.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Service

@Service
class SpellCasterService {

    @Autowired
    ContextService contextService

    static final SpelExpressionParser eval = new SpelExpressionParser()

    static Object evaluate(String expression, StandardEvaluationContext context) {
        return eval.parseExpression(expression).getValue(context)
    }

    static boolean validate(String expression) {
        // Validates the SpEL expression using the context object
        final SpelExpressionParser parser = new SpelExpressionParser()
        return parser.parseRaw(expression).compileExpression()
    }
}