package com.mpc.spellcaster.api

import com.mpc.spellcaster.service.ContextService
import com.mpc.spellcaster.service.RedisService
import com.mpc.spellcaster.service.SpellCasterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class SpellCasterController {

    @Autowired
    RedisService redisService

    @Autowired
    SpellCasterService spellCasterService

    @PostMapping("/validate")
    boolean validateExpression(@RequestBody String expression) {
        // Validates the SpEL expression using the context object

        return true
    }

    @PostMapping("/evaluate/{key}")
    Object evaluateExpression(@RequestBody String expression, @PathVariable String key) {
        // Evaluate the SpEL expression using the context object
        return spellCasterService.evaluate(expression, redisService.getContext(key))
    }
}