package com.tcs.ilp.servease.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }

    // =========================
    // RESOURCE NOT FOUND 
    // =========================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage()
        );
    }
    @ExceptionHandler(ServiceCenterException.class)
    public ResponseEntity<String> handleServiceCenter(ServiceCenterException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    /* ================================
    Business exception
    ================================ */
 @ExceptionHandler(ServiceCompletionExceptionMain.ServiceCompletionNotFoundException.class)
 public ResponseEntity<Map<String, Object>> handleNotFound(
         ServiceCompletionExceptionMain.ServiceCompletionNotFoundException ex) {

     return buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage());
 }
 
 /* ================================
 Catch-all exception (SAFE)
 ================================ */
@ExceptionHandler(Exception.class)
public ResponseEntity<Map<String, Object>> handleAllExceptions(
      Exception ex,
      HttpServletRequest request) throws Exception {

  String path = request.getRequestURI();

  // ✅ VERY IMPORTANT: DO NOT touch Swagger/OpenAPI endpoints
  if (path.startsWith("/v3/api-docs")
          || path.startsWith("/swagger-ui")
          || path.startsWith("/swagger-ui.html")) {
      throw ex; // let springdoc handle it
  }

  return buildResponse(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Internal Server Error",
          "An unexpected error occurred"
  );
}

    // =========================
    // INVALID INPUT 
    // =========================
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidInput(
            InvalidInputException ex) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid Input",
                ex.getMessage()
        );
    }

    // =========================
    // ILLEGAL ARGUMENT 
    // =========================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Illegal Argument",
                ex.getMessage()
        );
    }

    // =========================
    // COMMON RESPONSE BUILDER 
    // =========================
    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String error,
            String message) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);

        return ResponseEntity.status(status).body(response);
    }
}