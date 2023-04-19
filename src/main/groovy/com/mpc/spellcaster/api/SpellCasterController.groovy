package com.mpc.spellcaster.api

import com.mpc.spellcaster.service.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class SpellCasterController {

    @Autowired
    RedisService redisService

    @PostMapping("/context")
    void createContext(@RequestBody Map<String, Object> context) {
        redisService.storeContext(context)
    }

    @GetMapping("/context")
    Object getContext() {
        return redisService.getContext("context1", Map)
    }

    @PostMapping("/validate")
    boolean validateExpression(@RequestBody String expression) {
        // Validates the SpEL expression using the context object
        // ...
        return true
    }

    @PostMapping("/evaluate")
    Object evaluateExpression(@RequestBody String expression) {
        // Evaluate the SpEL expression using the context object
        return null
    }
}