package com.mpc.spellcaster.message

import com.mpc.spellcaster.service.ContextService
import groovy.transform.CompileStatic
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Component

@CompileStatic
@Component
class ContextSubscriber {

    private final ContextService contextService

    ContextSubscriber(ContextService contextService){
        this.contextService = contextService
    }

    @MessageMapping("context-updated")
    void handleMessage(ContextUploadedMessage contextUploadedMessage) {
        contextService.onUploadComplete(contextUploadedMessage)
    }
}