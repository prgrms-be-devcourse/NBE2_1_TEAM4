package com.example.coffee.controller;

import com.example.coffee.dto.ProductDTO;
import com.example.coffee.entity.Product;
import com.example.coffee.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@Transactional
@RequestMapping("/api/v1/products")

public class ProductController {
    private final ProductService productService;

    //C
    @PostMapping("/create")
    public ResponseEntity<?> productAdd(@Valid @RequestBody ProductDTO productDTO) {

        log.info("Adding product {}", productDTO);

        return ResponseEntity.ok(productService.register(productDTO));
    }

    //R
    @CrossOrigin(origins = "https://localhost:3001")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getOrder() {
        log.info("getOrder");
        return ResponseEntity.ok(productService.findAll());
    }

    //U
    @PutMapping("/modify")
    public ResponseEntity<?> productModify(@Valid @RequestBody ProductDTO productDTO) {
        log.info("Modifying product {}", productDTO);
        return ResponseEntity.ok(productService.modify(productDTO));
    }

    //D
    @DeleteMapping("/delete/{pid}")
    public ResponseEntity<Map<String,String>> productDelete(@PathVariable Long pid) {
        productService.remove(pid);
        return ResponseEntity.ok(Map.of("delete","success"));
    }

}
