package com.example.gccoffee.service;

import com.example.gccoffee.dto.OrderDto;
import com.example.gccoffee.entity.*;
import com.example.gccoffee.entity.product.Product;
import com.example.gccoffee.repository.MemberRepository;
import com.example.gccoffee.repository.OrderRepository;
import com.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long order(OrderDto orderDto) {
        // 이메일로 멤버 조회
        Member member = memberRepository.findByEmail(orderDto.getEmail());

        // 멤버가 존재하지 않으면 예외 처리
        if (member == null) {
            throw new IllegalArgumentException("No member found with email: " + orderDto.getEmail());
        }

        // 주문 상품 생성
        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(orderItemDto -> {
                    Product product = productRepository.findOne(orderItemDto.getProductId());
                    return OrderItem.createOrderItem(product, product.getPrice(), orderItemDto.getQuantity());
                })
                .collect(Collectors.toList());

        // 주문 생성
        Order order = Order.createOrder(
                member, // Member
                new Email(orderDto.getEmail()), // 이메일
                new Address(
                        orderDto.getAddress(), // city
                        "", // street은 비워두거나, 필요에 따라 설정
                        orderDto.getPostcode() // postcode
                ), // 주소
                orderItems.toArray(new OrderItem[0])
        );

        // 주문 저장
        orderRepository.save(order);

        return order.getId(); // `Long` 타입 반환
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order != null) {
            order.cancel();
        }
    }

    public Order findOne(Long id) {
        return orderRepository.findOne(id);
    }

    @Transactional
    public void processOrdersForPeriod() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.minusDays(1).withHour(14).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = now.withHour(14).withMinute(0).withSecond(0);

        List<Order> orders = orderRepository.findOrdersBetween(startOfDay, endOfDay);

        // 주문 처리 로직을 여기에 추가합니다
    }

    // 이메일로 주문을 찾는 메서드
    public Order findOneByEmail(String email) {
        return orderRepository.findByEmail(email);
    }
}
