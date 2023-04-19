package com.mpc.spellcaster.api

import com.mpc.spellcaster.model.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.expression.Expression
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/spellcaster")
class SpellCasterController {
    @Autowired
    RedisTemplate<String, Context> redisTemplate

    @PostMapping("/evaluate")
    Object evaluateExpression(@RequestBody Context context, @RequestParam String expression) {
        final SpelExpressionParser parser = new SpelExpressionParser()
        Expression exp = parser.parseExpression(expression)
        return exp.getValue(context.contextMap)
    }
}