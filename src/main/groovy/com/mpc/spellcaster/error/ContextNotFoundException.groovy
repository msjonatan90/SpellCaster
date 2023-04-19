package com.mpc.spellcaster.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ContextNotFoundException extends RuntimeException {
    ContextNotFoundException(String message) {
        super(message)
    }
}