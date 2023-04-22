package com.mpc.spellcaster.api

import com.mpc.spellcaster.service.ContextUploadService
import com.mpc.spellcaster.service.ContextService
import com.mpc.spellcaster.service.SpellCasterService
import com.mpc.spellcaster.util.SpelExpressionParser
import groovy.json.JsonOutput
import org.springframework.expression.EvaluationContext
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*

import static org.springframework.http.MediaType.*

@RestController
class SpellCasterController {

    private final ContextService contextService

    private final SpellCasterService spellCaster

    private final ContextUploadService contextUploadService

    SpellCasterController(ContextService contextService,
                          SpellCasterService spellCaster,
                          ContextUploadService contextUploadService) {
        this.contextService = contextService
        this.spellCaster = spellCaster
        this.contextUploadService = contextUploadService
    }

    /**
     * Create a new context for the given app
     * @param appName
     * @param context
     * @return
     */
    @PostMapping("/context/{appName}")
    String createContext(@PathVariable String appName, @RequestBody String context) {
        return contextService.create(appName, context)
    }

    /**
     * Create a new context for the given app based on the uploaded file
     * @param appName
     * @param contextFile
     * @return
     */
    @PostMapping(value = "/context/{appName}/file", consumes = MULTIPART_FORM_DATA_VALUE)
    String createContext(@PathVariable String appName, @RequestPart("context") FilePart contextFile) {
        try {
            String contextKey = UUID.randomUUID().toString()
            contextUploadService.uploadContext(appName, contextKey, contextFile)
            return contextKey
        } catch (IOException e) {
            throw new RuntimeException("Failed to read context file", e) // use custom exception
        }
    }

    /**
     * Update the context for the given app
     * @param appName
     * @param key
     * @param context
     */
    @PutMapping("/context/{appName}/{key}")
    void putContext(@PathVariable String appName, @PathVariable String key, @RequestBody String context) {
        contextService.update(appName, key, context)
    }

    /**
     * Delete the context for the given app
     * @param appName
     * @param key
     */
    @DeleteMapping("/context/{appName}/{key}")
    void deleteContext(@PathVariable String appName, @PathVariable String key) {
        contextService.delete(appName, key)
    }

    /**
     * Get the context for the given app
     * @param appName
     * @param key
     * @return
     */
    @GetMapping("/context/{appName}/{key}")
    String getContext(@PathVariable String appName, @PathVariable String key) {
        // Get the context for the given app and key
        EvaluationContext context = contextService.get(appName, key)
        // Return the context as a JSON string
        return JsonOutput.prettyPrint(JsonOutput.toJson(context.getRootObject()?.getValue()))
    }

    /**
     * Evaluate the SpEL expression using the context object
     * @param appName
     * @param key
     * @param expression
     * @return
     */
    @PostMapping( value = "/context/{appName}/{key}/eval", consumes = TEXT_PLAIN_VALUE)
    Object evaluateExpression(@PathVariable String appName, @PathVariable String key, @RequestBody String expression) {
        // Evaluate the SpEL expression using the context object
        return JsonOutput.prettyPrint(JsonOutput.toJson(
                //evaluate the expression and return the result
                spellCaster.evaluate(appName, key, expression)?.toString()))
    }

    /**
     * Validates the SpEL expression using the context object
     * @param expression
     * @return
     */
    @PostMapping(value = "/expression/validate", consumes = TEXT_PLAIN_VALUE)
    boolean validateExpression(@RequestBody String expression) {
        // Validates the SpEL expression using the context object
        return spellCaster.validate(expression) //TODO not working
    }

    /**
     * Evaluate the SpEL expression using the context object
     * @param appName
     * @param key
     * @param expression
     * @return
     */
    @PostMapping(value = "/context/{appName}/{key}/evalJson", consumes = APPLICATION_JSON_VALUE)
    Object evaluateJSONExpression(@PathVariable String appName, @PathVariable String key, @RequestBody String jsonExpression) {

        //TODO implement new class for parsing JSON expression into SpEL expression considering all the chucks of ideas, proposals, requirement and details for this module at the folder src/main/resources/json_to_spel

        String SpELExpression = new SpelExpressionParser().parseJsonToSpel(jsonExpression)

        //TODO validate the JSON expression using that new class

        //TODO parse the JSON expression into SpEL Expression using the module



        //TODO evaluate the SpEL expression using the context object

        //TODO return the result of the evaluation as a JSON string

        return evaluateExpression(appName, key, SpELExpression)
    }

    /**
     * Validates the SpEL expression using the context object
     * @param expression
     * @return
     */
    @PostMapping(value = "/expression/validate", consumes = APPLICATION_JSON_VALUE)
    boolean validateJSONExpression(@RequestBody String jsonExpression) {
        //TODO validate the JSON expression. Similar to evaluateJSONExpression, but without evaluating, just validating
        return false //TODO return the result of the validation
    }
}
