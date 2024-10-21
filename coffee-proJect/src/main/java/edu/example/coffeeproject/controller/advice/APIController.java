package edu.example.coffeeproject.controller.advice;

import edu.example.coffeeproject.exception.OrderTaskException;
import edu.example.coffeeproject.exception.ProductTaskException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Log4j2
public class APIController {
    @ExceptionHandler(OrderTaskException.class)
    public ResponseEntity<Map<String, String>> handleException(OrderTaskException e) {
        log.info("--- OrderTaskException");
        log.info("--- e.getClass().getName() : " + e.getClass().getName());
        log.info("--- e.getMessage() : " + e.getMessage());

        Map<String, String> errMap = Map.of("error", e.getMessage());

        return ResponseEntity.status(e.getCode()).body(errMap);
    }
    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<Map<String, String>> handleException(ProductTaskException e) {
        log.info("--- ProductTaskException");
        log.info("--- e.getClass().getName() : " + e.getClass().getName());
        log.info("--- e.getMessage() : " + e.getMessage());

        Map<String, String> errMap = Map.of("error", e.getMessage());

        return ResponseEntity.status(e.getCode()).body(errMap);
    }
}
