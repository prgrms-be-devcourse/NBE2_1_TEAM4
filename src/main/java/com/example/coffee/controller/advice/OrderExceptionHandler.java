package com.example.coffee.controller.advice;


import com.example.coffee.exception.OrderTaskException;
import com.example.coffee.exception.ProductTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;


public class OrderExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderExceptionHandler.class);

    @ExceptionHandler(OrderTaskException.class)
    public ResponseEntity<Map<String, Object>> handleOrderException(OrderTaskException e){
        log.info("---OrderTaskException---");
        Map<String, Object> errmap = Map.of("error", e.getMessage());

        return ResponseEntity.status(e.getCode()).body(errmap);
    }

    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<Map<String, Object>> handleOrderException(ProductTaskException e){
        log.info("---ProductTaskException---");
        Map<String, Object> errmap = Map.of("error", e.getMessage());
        return ResponseEntity.status(e.getCode()).body(errmap);
    }
    //UnexpectedTypeException

}
