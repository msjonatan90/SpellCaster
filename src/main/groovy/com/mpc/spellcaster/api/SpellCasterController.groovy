package com.mpc.spellcaster.api

import com.mpc.spellcaster.service.ContextService
import com.mpc.spellcaster.service.SpellCasterService
import groovy.json.JsonOutput
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*

@RestController
class SpellCasterController {

    private final ContextService contextService

    private final SpellCasterService spellCasterService

    SpellCasterController(ContextService contextService,
                          SpellCasterService spellCasterService) {
        this.contextService = contextService
        this.spellCasterService = spellCasterService
    }

    @PostMapping("/context/{appName}")
    String createContext(@PathVariable String appName, @RequestBody String context) {
        return contextService.create(appName, context)
    }

//    @PostMapping(value = "/context/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    String createContext(@RequestPart("context") FilePart context) {
////        return context.contentAsString.flatMap(contextService.opsForValue()::set)
//        return null;
//    }

    @PutMapping("/context/{appName}/{key}")
    void putContext(@PathVariable String appName, @PathVariable String key, @RequestBody String context) {
        contextService.update(appName, key, context)
    }

    @DeleteMapping("/context/{appName}/{key}")
    void deleteContext(@PathVariable String appName, @PathVariable String key) {
        contextService.delete(appName, key)
    }

    @GetMapping("/context/{appName}/{key}")
    String getContext(@PathVariable String appName, @PathVariable String key) {
        StandardEvaluationContext context = contextService.get(appName, key)
        return JsonOutput.prettyPrint(JsonOutput.toJson(context.getRootObject()?.getValue()))
    }

    @PostMapping("/expression/validate")
    boolean validateExpression(@RequestBody String expression) {
        // Validates the SpEL expression using the context object
        return spellCasterService.validate(expression)
    }

    @PostMapping("/expression/evaluate/{appName}/{key}")
    Object evaluateExpression(@PathVariable String appName, @PathVariable String key, @RequestBody String expression) {
        // Evaluate the SpEL expression using the context object
        return JsonOutput.prettyPrint(JsonOutput.toJson(
                spellCasterService.evaluate(expression, contextService.get(appName, key))))
    }
}