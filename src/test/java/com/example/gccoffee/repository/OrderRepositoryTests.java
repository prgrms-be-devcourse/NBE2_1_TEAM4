package com.example.gccoffee.repository;

import com.example.gccoffee.entity.*;
import com.example.gccoffee.exception.ProductException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    //주문 등록 테스트
    @Test
    public void testInsertOrder() {
        Product product = productRepository.findById(1L).orElseThrow(ProductException.NOT_FOUND::get);

        Order order = Order.builder()
                            .email("test@example.com")
                            .address("서울시 짱구동 짱아파트")
                            .postcode(12345)
                            .orderStatus(OrderStatus.ACCEPTED)
                            .build();

        OrderItem orderItem = OrderItem.builder()
                                        .product(product)
                                        .order(order)
                                        .category(Category.COFFEE_BEAN_PACKAGE)
                                        .price(product.getPrice())
                                        .quantity(2)
                                        .build();

        orderItem.setOrder(order);

        order.setOrderItems(List.of(orderItem));

        orderRepository.save(order);

        assertNotNull(order.getOrderId());
        assertNotNull(orderItem.getOrderItemId());
    }

    //주문 조회 테스트
    @Test
    @Transactional(readOnly = true)
    public void testRead() {
        Long orderId = 1L;
        Order order = orderRepository.findById(orderId).orElseThrow();
        assertNotNull(order);
    }

    //주문 수정 테스트
    @Test
    @Transactional
    @Commit
    public void testUpdate() {
        Long orderId = 1L;
        String email = "testUpdate@example.com";
        String address = "서울시 수정구 테스트동";
        int postcode = 12345;

        Order order = orderRepository.findById(orderId).orElseThrow();

        order.changeAddress(address);
        order.changePostcode(postcode);
        order.changeOrderStatus(OrderStatus.CANCELLED);

        order = orderRepository.findById(orderId).orElseThrow();

        assertEquals(address, order.getAddress());
        assertEquals(postcode, order.getPostcode());
        assertEquals(order.getOrderStatus(), OrderStatus.CANCELLED);
    }

    //주문 삭제 테스트
    @Test
    public void testDelete() {
        Long orderId = 1L;
        orderRepository.deleteById(orderId);
        assertTrue(orderRepository.findById(orderId).isEmpty());
    }

    //주문 리스트 테스트
    @Test
    public void testFindAll() {
        orderRepository.findAll().forEach(p -> log.info(p.getOrderId()));
    }
}
