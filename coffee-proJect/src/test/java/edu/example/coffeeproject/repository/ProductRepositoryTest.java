package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ProductRepositoryTest {

    private final ProductRepository productRepository;

    @Autowired
    ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
//    @AfterEach
//    public void cleanup() {
//        productRepository.deleteAll();
//    }
    @Test
    public void TestGet(){
        Product product = Product.builder().productId(1L).productName("splite").category(Category.ETHIOPIA_SIDAME).
                price(1000).description("splite eat").build();
        productRepository.save(product);
        log.info(product);
        assertEquals(product.getProductId(), 1L);
        assertEquals(product.getProductName(), "splite");

    }

    @Test
    public void TestRead(){
        Long productId = 9L;

        Optional<Product> product = productRepository.findById(productId);

        Product product1 = product.orElseThrow(() -> new RuntimeException("Product not found"));

        log.info("product1 : " +product1);

        assertEquals(product1.getProductId(), productId);
    }

    @Test
    public void TestUpdate(){
        Long productId = 9L;

        Optional<Product> product = productRepository.findById(productId);

        Product product1 = product.orElseThrow(() -> new RuntimeException("Product not found"));

        try {
            product1.changeProductName("치킨");
            product1.changeCategory(Category.ETHIOPIA_SIDAME);
            product1.changePrice(product1.getPrice() * 3);
            product1.changeDescription("치킨먹는법");

        }catch (Exception e){
            log.error(e.getMessage());
        }

        productRepository.save(product1);

        log.info("product1 : " +product1);
    }

    @Test
    public void TestDelete(){
        Long productId = 9L;

        Optional<Product> product = productRepository.findById(productId);
        Product product1 = product.orElse(null);

        assertNotNull(product1);

        productRepository.delete(product1);

        assertFalse(productRepository.findById(productId).isPresent()," should not find product");
    }
}