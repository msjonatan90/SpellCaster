package com.mpc.spellcaster.api

import com.mpc.spellcaster.cache.ContextUploadService
import com.mpc.spellcaster.service.ContextService
import com.mpc.spellcaster.service.SpellCaster
import groovy.json.JsonOutput
import org.springframework.expression.EvaluationContext
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*

@RestController
class SpellCasterController {

    private final ContextService contextService

    private final SpellCaster spellCaster

    private final ContextUploadService contextUploadService

    SpellCasterController(ContextService contextService,
                          SpellCaster spellCaster,
                          ContextUploadService contextUploadService) {
        this.contextService = contextService
        this.spellCaster = spellCaster
        this.contextUploadService = contextUploadService
    }

    @PostMapping("/context/{appName}")
    String createContext(@PathVariable String appName, @RequestBody String context) {
        return contextService.create(appName, context)
    }

    @PostMapping(value = "/context/{appName}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String createContext(@PathVariable String appName, @RequestPart("context") FilePart contextFile) {
        try {
            String contextKey = UUID.randomUUID().toString()
            contextUploadService.uploadContext(appName, contextKey, contextFile)
            return contextKey
        } catch (IOException e) {
            throw new RuntimeException("Failed to read context file", e) // use custom exception
        }
    }

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
        EvaluationContext context = contextService.get(appName, key)
        return JsonOutput.prettyPrint(JsonOutput.toJson(context.getRootObject()?.getValue()))
    }

    @PostMapping("/context/{appName}/{key}/eval")
    Object evaluateExpression(@PathVariable String appName, @PathVariable String key, @RequestBody String expression) {
        // Evaluate the SpEL expression using the context object
        return JsonOutput.prettyPrint(JsonOutput.toJson(
                spellCaster.evaluate(appName, key, expression)?.toString()))
    }

    @PostMapping("/expression/validate")
    boolean validateExpression(@RequestBody String expression) {
        // Validates the SpEL expression using the context object
        return spellCaster.validate(expression)
    }
}