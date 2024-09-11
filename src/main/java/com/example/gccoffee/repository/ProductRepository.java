package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Category;
import com.example.gccoffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByProductName(String productName);

    List<Product> findByCategory(Category category);
}
