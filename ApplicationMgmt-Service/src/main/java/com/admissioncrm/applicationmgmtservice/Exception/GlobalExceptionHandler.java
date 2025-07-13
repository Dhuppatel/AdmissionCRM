package com.admissioncrm.applicationmgmtservice.Exception;


import com.admissioncrm.applicationmgmtservice.Dto.ApiResponse;
import com.admissioncrm.applicationmgmtservice.Dto.ErrorResponse;
import com.admissioncrm.applicationmgmtservice.Exception.Document.DocumentNotFoundException;
import com.admissioncrm.applicationmgmtservice.Exception.Feign.ForbiddenException;
import com.admissioncrm.applicationmgmtservice.Exception.Feign.UnauthorizedException;
import feign.FeignException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation errors (DTO field validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    //  Data Integrity errors (e.g. NOT NULL constraint violations)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Database integrity error");
        response.put("message", ex.getMostSpecificCause().getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Validation annotations like @NotBlank
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Constraint violation");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    // Application not found
    @ExceptionHandler(ApplicationFormNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleApplicationNotFound(ApplicationFormNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Application not found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //  Invalid application data
    @ExceptionHandler(InvalidApplicationDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidData(InvalidApplicationDataException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid application data");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //  Duplicate application
    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateApplication(DuplicateApplicationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Duplicate application");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    //invalid workflow transection

    @ExceptionHandler(InvalidWorkflowTransitionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidWorkflowTransition(InvalidWorkflowTransitionException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid workflow transition");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    //  Generic fallback for all unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal server error");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handelDocumentNotFound(DocumentNotFoundException ex) {

        return ResponseEntity.badRequest().body(ApiResponse.error("Document not Found", ex.getMessage(),400));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {

        HttpStatus status = HttpStatus.valueOf(e.status());
        String message = switch (e.status()) {
            case 401 -> "Authentication required. Please login.";
            case 403 -> "Access denied. Insufficient permissions.";
            case 404 -> "Service endpoint not found.";
            case 500 -> "Internal server error occurred.";
            case 503 -> "Service temporarily unavailable.";
            default -> "Service communication error occurred.";
        };

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .errorCode("FEIGN_ERROR")
                .status(e.status())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}