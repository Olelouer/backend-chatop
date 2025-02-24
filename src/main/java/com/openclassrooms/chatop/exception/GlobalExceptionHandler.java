package com.openclassrooms.chatop.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that provides centralized exception handling across all @RequestMapping methods.
 * Provides consistent error responses for different types of exceptions in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors that occur during @Valid validation.
     * Processes field-level validation errors and returns them in a map.
     *
     * @param ex The validation exception containing binding errors
     * @return Map of field names to error messages with 400 Bad Request status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles illegal argument exceptions for invalid input parameters.
     * Returns a 400 Bad Request status with the error message.
     *
     * @param ex The IllegalArgumentException that was thrown
     * @return Error message with 400 Bad Request status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles constraint violation exceptions that occur during validation.
     * Processes property-level constraint violations and returns them in a map.
     *
     * @param ex The ConstraintViolationException that was thrown
     * @return Map of property paths to error messages with 400 Bad Request status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles cases where requested entities are not found in the database.
     * Returns a 404 Not Found status with the error message.
     *
     * @param ex The EntityNotFoundException that was thrown
     * @return Error message with 404 Not Found status
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles access denied exceptions for unauthorized access attempts.
     * Returns a 403 Forbidden status with the error message.
     *
     * @param ex The AccessDeniedException that was thrown
     * @return Error message with 403 Forbidden status
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Forbidden: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles authentication exceptions for unauthenticated access attempts.
     * Returns a 401 Unauthorized status with the error message.
     *
     * @param ex The AuthenticationException that was thrown
     * @return Error message with 401 Unauthorized status
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unauthorized: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Fallback handler for all unhandled exceptions.
     * Returns a 401 Unauthorized status with no message body, as per project requirements.
     *
     * @return Empty response with 401 Unauthorized status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleAllExceptions() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
