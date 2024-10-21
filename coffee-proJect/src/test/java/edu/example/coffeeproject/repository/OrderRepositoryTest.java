package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class OrderRepositoryTest {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepositoryTest(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Test
    public void add() {
        Long productId = 1L;
        Optional<Product> product = productRepository.findById(productId);
        Order order = Order.builder()
                .email("ggg@naver.com")
                .address("여수밤바다")
                .postCode("123-321")
                .orderStatus(OrderStatus.ACCEPTED)
                .build();
        orderRepository.save(order);
        assertEquals(order.getAddress(), "여수밤바다");

        OrderItem orderItem = OrderItem.builder()
                .product(product.get())
                .order(order)
                .category(Category.BRAZILL_SERRA_DO_COFFEE)
                .price(1000)
                .quantity(5)
                .build();

        orderItemRepository.save(orderItem);
    }

    @Test
    public void read(){
        String email = "bbb@naver.com";

        Optional<Order> findOrder = orderRepository.findByEmail(email);

        Order order = findOrder.orElse(null);

        log.info(order);
    }

    @Test
    public void update(){
        String email = "bbb@naver.com";

        Optional<Order> findOrder = orderRepository.findByEmail(email);

        Order order = findOrder.orElse(null);

        try {
            order.changeAddress("신기동");
            order.changePostCode("234-232");
            order.changeOrderStatus(OrderStatus.ACCEPTED);

            orderRepository.save(order);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        assertEquals(order.getAddress(), "신기동");
    }

    @Test
    public void delete(){
        String email = "bbb@naver.com";
        Optional<Order> findOrder = orderRepository.findByEmail(email);
        Order order = findOrder.orElse(null);
        orderRepository.delete(order);

    }
}


