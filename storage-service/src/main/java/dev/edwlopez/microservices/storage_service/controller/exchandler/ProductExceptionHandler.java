package dev.edwlopez.microservices.storage_service.controller.exchandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> anyException (Exception e) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", 500);
        map.put("error_msg", e.getMessage());

        return ResponseEntity.internalServerError().body(map);
    }
}
