package com.example.coffee.repositroy;


import com.example.coffee.entity.*;

import com.example.coffee.exception.OrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderRepositoryTests {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;



    @Test
    @Commit
    public void test(){
        orderItemRepository.save(OrderItem.builder().orderItemId(2L).price(10000).quantity(5).category(Category.COFFEE_BEAN_PACKAGE).product(Product.builder().pid(1L).build()).order(Order.builder().oid(1L).build()).build());
        OrderItem orderItem = orderItemRepository.findById(2L).orElse(null);
        System.out.println("orderItem = " + orderItem);
    }

    @Test
    @Transactional
    @Commit
    public void add(){
        Long oid = 5L;
        Random random = new Random();
        int quantity = 5;
        int price = 10000;
        String email= "aaa@aaa.com";
        IntStream.rangeClosed(0,10).forEach(i->{
            Optional<Order> foundOrder = orderRepository.findById(oid);
            Order savedOrder = foundOrder.orElseGet(()-> {Order order= Order.builder().address("테스트 주소").postcode(15776L).email(email).orderStatus(OrderStatus.ACCEPTED).build();
                return orderRepository.save(order);
            });
            Product product = productRepository.findById(random.nextLong(4)+1).orElse(null);
            OrderItem orderItem = OrderItem.builder().quantity(quantity).price(product.getPrice()).category(product.getCategory()).order(savedOrder).product(product).build();
            OrderItem save = orderItemRepository.save(orderItem);
            savedOrder.getOrderItems().add(orderItem);
            assertNotNull(save);
        });

    }

    @Test
    @Transactional
    public void findByEmail() {
        List<OrderItem> byEmail = orderRepository.findByEmail("aaa@aaa.com").get().getOrderItems();
        byEmail.forEach(System.out::println);
    }

    @Test
    public void random(){
        Random random = new Random();
        LongStream longs = random.longs(1, 4);
    }

    @Test
    public void deleteAll(){
        String email = "aaa@aaa.com";
        try {
            List<OrderItem> byEmails = orderRepository.findByEmails(email).get();
            for (OrderItem byEmail : byEmails) {
                orderItemRepository.delete(byEmail);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw OrderException.NOT_MODIFIED.get();
        }
    }
}
