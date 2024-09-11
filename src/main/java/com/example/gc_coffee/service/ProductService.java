package com.example.gc_coffee.service;

import com.example.gc_coffee.entity.product.CoffeeType;
import com.example.gc_coffee.entity.product.Product;
import com.example.gc_coffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, String productName, CoffeeType type, long price, String description) {
        Product product = productRepository.findOne(productId);
        product.setProductName(productName);
        product.setType(type);
        product.setPrice(price);
        product.setDescription(description);
    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    public Product findOne(Long productId) {
        return productRepository.findOne(productId);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findOne(productId);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }
}
