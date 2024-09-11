package com.example.coffee.repositroy;

import com.example.coffee.entity.Category;
import com.example.coffee.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insert(){
        productRepository.save(Product.builder().category(Category.COFFEE_BEAN_PACKAGE)
                .description("에티오피아 커피 빈").name("Ethiopia Sidamo").price(8000).build());
    }

}