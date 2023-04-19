package com.mpc.spellcaster.service

import com.mpc.spellcaster.model.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.expression.Expression
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Service

@Service
class SpellCasterService {

    @Autowired
    ContextService contextService

    static Object evaluate(String expression, Context context) {
        final SpelExpressionParser parser = new SpelExpressionParser()
        final Expression exp = parser.parseExpression(expression)
        return exp.getValue(context)
    }

    static boolean validate(String expression) {
        // Validates the SpEL expression using the context object
        final SpelExpressionParser parser = new SpelExpressionParser()
        return parser.parseRaw(expression).compileExpression()
    }
}