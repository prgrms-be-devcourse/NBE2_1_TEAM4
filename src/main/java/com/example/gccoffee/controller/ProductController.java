package com.example.gccoffee.controller;

import com.example.gccoffee.dto.product.ProductResponseDTO;
import com.example.gccoffee.entity.Product;
import com.example.gccoffee.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Log4j2
@Tag(name = "상품", description = "상품 CRUD")
public class ProductController {
    private final ProductService productService;

    //상품 등록
    @PostMapping
    @Operation(summary = "상품 등록", description = "상품을 등록할 때 사용하는 API")
    public ResponseEntity<ProductResponseDTO> register(@RequestBody ProductResponseDTO productResponseDTO) {
        log.info("Registering new product");
        return ResponseEntity.ok(productService.register(productResponseDTO));
    }

    //상품 조회
    @GetMapping("/{productId}")
    @Operation(summary = "상품 조회", description = "상품 1개를 조회할 때 사용하는 API")
    public ResponseEntity<ProductResponseDTO> read(@PathVariable Long productId) {
        log.info("Getting product by ID");
        return ResponseEntity.ok(productService.read(productId));
    }

    //상품 수정
    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품 1개를 수정할 때 사용하는 API")
    public ResponseEntity<ProductResponseDTO> modify(@PathVariable Long productId, @RequestBody ProductResponseDTO productResponseDTO) {
        log.info("Updating product by ID");
        return ResponseEntity.ok(productService.modify(productId, productResponseDTO));
    }

    //상품 삭제
    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "상품 1개를 삭제할 때 사용하는 API")
    public ResponseEntity<Map<String, String>> remove(@PathVariable Long productId) {
        log.info("Removing product by ID");
        productService.remove(productId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    //상품 리스트
    @GetMapping
    @Operation(summary = "상품 리스트 조회", description = "상품 리스트를 조회할 때 사용하는 API")
    public ResponseEntity<List<Product>> readAll() {
        log.info("Getting all products");
        return ResponseEntity.ok(productService.readAll());
    }
}
