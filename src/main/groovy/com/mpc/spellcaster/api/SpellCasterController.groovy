package com.mpc.spellcaster.api

import com.mpc.spellcaster.model.Context
import com.mpc.spellcaster.service.ContextService
import com.mpc.spellcaster.service.RedisService
import com.mpc.spellcaster.service.SpellCasterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class SpellCasterController {

    @Autowired
    private ContextService contextService

    @Autowired
    private SpellCasterService spellCasterService

    @PostMapping("/{key}")
    String putContext(@PathVariable String key, @RequestBody Context value) {
        return contextService.put(key, value)
    }

    @DeleteMapping("/{key}")
    void deleteContext(@PathVariable String key) {
        contextService.delete(key);
    }

    @GetMapping("/{key}")
    Object getContext(@PathVariable String key) {
        return contextService.get(key)
    }

    @PostMapping("/validate")
    boolean validateExpression(@RequestBody String expression) {
        // Validates the SpEL expression using the context object
        return spellCasterService.validate(expression)
    }

    @PostMapping("/evaluate/{key}")
    Object evaluateExpression(@RequestBody String expression, @PathVariable String key) {
        // Evaluate the SpEL expression using the context object
        return spellCasterService.evaluate(expression, contextService.get(key))
    }
}