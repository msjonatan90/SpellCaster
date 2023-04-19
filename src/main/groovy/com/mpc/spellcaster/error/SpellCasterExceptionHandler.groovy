package com.mpc.spellcaster.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SpellCasterExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<String> handleException(Exception ex) {
        // Log the exception
        logger.error("An error occurred: ", ex);

        // Return an error response to the client
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(value = ContextNotFoundException.class)
    ResponseEntity<String> handleContextNotFoundException(ContextNotFoundException ex) {
        // Log the exception
        logger.error("Context not found: ", ex);

        // Return an error response to the client
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Context not found: " + ex.getMessage());
    }

    @ExceptionHandler(value = ExpressionEvaluationException.class)
    ResponseEntity<String> handleExpressionEvaluationException(ExpressionEvaluationException ex) {
        // Log the exception
        logger.error("Expression evaluation failed: ", ex);

        // Return an error response to the client
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Expression evaluation failed: " + ex.getMessage());
    }
}