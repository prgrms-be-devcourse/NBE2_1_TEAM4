package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Category;
import com.example.gccoffee.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    //상품 등록 테스트
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 2).forEach(i -> {
            Product product = Product.builder().productName("productName").category(Category.COFFEE_BEAN_PACKAGE).price(5000).description("description").build();

            Product savedProduct = productRepository.save(product);

            assertNotNull(savedProduct);
            assertEquals(i, savedProduct.getProductId());
        });
    }

    //상품 조회 테스트
    @Test
    @Transactional(readOnly = true)
    public void testRead() {
        Long productId = 1L;
        Product product = productRepository.findById(productId).orElse(null);

        assertNotNull(product);
    }

    //상품 수정 테스트
    @Test
    @Transactional
    @Commit
    public void testUpdate() {
        Long productId = 1L;
        String productName = "New productName";
        Category category = Category.COFFEE_BEAN_PACKAGE;
        int price = 13000;
        String description = "New description";

        Product product = productRepository.findById(productId).orElse(null);

        assertNotNull(product);

        product.changeProductName(productName);
        product.changeCategory(category);
        product.changePrice(price);
        product.changeDescription(description);

        product = productRepository.findById(productId).orElse(null);

        assertEquals(productName, product.getProductName());
        assertEquals(category, product.getCategory());
        assertEquals(price, product.getPrice());
        assertEquals(description, product.getDescription());
    }

    //상품 삭제 테스트
    @Test
    public void testDelete() {
        Long productId = 2L;

        productRepository.deleteById(productId);

        assertTrue(productRepository.findById(productId).isEmpty());
    }

    //상품 리스트 테스트
    @Test
    public void testFindAll() {
        productRepository.findAll().forEach(p -> log.info(p.getProductId()));
    }
}
