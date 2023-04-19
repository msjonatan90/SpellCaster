package com.mpc.spellcaster.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ExpressionEvaluationException extends RuntimeException {
    ExpressionEvaluationException(String message) {
        super(message)
    }
}
