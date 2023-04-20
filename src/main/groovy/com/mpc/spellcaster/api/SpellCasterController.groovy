package com.mpc.spellcaster.api

import com.mpc.spellcaster.service.ContextService
import com.mpc.spellcaster.service.SpellCasterService
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class SpellCasterController {

    @Autowired
    private ContextService contextService

    @Autowired
    private SpellCasterService spellCasterService

    @PostMapping("/context")
    String createContext(@RequestBody Flux<String> contextStream) {
        contextStream.subscribe (context -> { println "TEST: " + context})
        return contextService.create(contextStream)
    }
//
//    @PostMapping(value = "/context", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    Mono<String> createContext(@RequestPart("context") FilePart context) {
//        return context.contentAsString.flatMap(redisTemplate.opsForValue()::set)
//    }

    @PutMapping("/context/{key}")
    void putContext(@PathVariable String key, @RequestBody String context) {
        contextService.update(key, context)
    }

    @DeleteMapping("/context/{key}")
    void deleteContext(@PathVariable String key) {
        contextService.delete(key)
    }

    @GetMapping("/context/{key}")
    String getContext(@PathVariable String key) {
        StandardEvaluationContext context = contextService.get(key)
        return JsonOutput.toJson(context.getRootObject()?.getValue())
    }

    @PostMapping("/expression/validate")
    boolean validateExpression(@RequestBody String expression) {
        // Validates the SpEL expression using the context object
        return spellCasterService.validate(expression)
    }

    @PostMapping("/expression/evaluate/{key}")
    Object evaluateExpression(@RequestBody String expression, @PathVariable String key) {
        // Evaluate the SpEL expression using the context object
        return JsonOutput.prettyPrint(JsonOutput.toJson(
                spellCasterService.evaluate(expression, contextService.get(key))))
    }
}