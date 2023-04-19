package com.mpc.spellcaster.api

import com.mpc.spellcaster.service.ContextService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/context")
class ContextController {
    @Autowired
    private ContextService contextService

    @PostMapping("/{key}")
    void putContext(@PathVariable String key, @RequestBody Object value) {
        contextService.put(key, value)
    }

    @DeleteMapping("/{key}")
    void deleteContext(@PathVariable String key) {
        contextService.delete(key);
    }

    @GetMapping("/{key}")
    Object getContext(@PathVariable String key) {
        return contextService.get(key)
    }
}