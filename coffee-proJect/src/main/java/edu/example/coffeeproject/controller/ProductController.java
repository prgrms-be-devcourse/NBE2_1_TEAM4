package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1/coffee")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> Register(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.add(productDTO));
    }

    @GetMapping("{pid}")
    public ResponseEntity<ProductDTO> getProducts(@PathVariable("pid") Long pid) {
        return ResponseEntity.ok(productService.read(pid));
    }

    @PutMapping("{pid}")
    public ResponseEntity<ProductDTO> Modify(@PathVariable("pid") Long pid, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.update(productDTO));
    }

    @DeleteMapping("{pid}")
    public void delete(@PathVariable("pid") Long pid) {
        productService.remove(pid);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.readAll());
    }
}
