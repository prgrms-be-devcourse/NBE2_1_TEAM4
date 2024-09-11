package com.example.gccoffee.service;



import com.example.coffee.dto.OrderDTO;
import com.example.coffee.dto.OrderItemDTO;
import com.example.coffee.entity.Order;
import com.example.coffee.entity.OrderItem;
import com.example.coffee.entity.OrderStatus;
import com.example.coffee.entity.Product;
import com.example.coffee.exception.OrderException;
import com.example.coffee.repositroy.OrderItemRepository;
import com.example.coffee.repositroy.OrderRepository;
import com.example.coffee.repositroy.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderItemRepository orderItemRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }



    public OrderDTO findByEmail(String email) {
        Order order = orderRepository.findByEmail(email).orElseThrow(OrderException.ORDER_NOT_FOUND::get);
        return new OrderDTO(order);
    }

    public List<OrderItemDTO> findByEmails(String email) {
        Order order = orderRepository.findByEmail(email).orElse(null);
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        if (!orderItems.isEmpty()) {
            for (OrderItem orderItem : orderItems) {
               orderItemDTOS.add(new OrderItemDTO(orderItem));
            }
        }else throw OrderException.ORDER_NOT_FOUND.get();

        return orderItemDTOS;
    }

    public OrderDTO add(OrderDTO orderDTO) {

        Order order = orderDTO.toEntity(orderDTO);
        order = orderRepository.save(order);
        List<OrderItem> orderItems = orderDTO.getOrderItems();
        assertNotNull(orderItems);

        for (OrderItem orderItem : orderItems) {
            OrderItem.builder().order(orderItem.getOrder())
                    .category(orderItem.getCategory())
                    .product(orderItem.getProduct())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();
            orderItemRepository.save(orderItem);
        }
        return orderDTO;
    }

    public OrderItemDTO update(OrderDTO orderDTO){
//        Optional<Order> foundOrder = orderRepository.findByEmail(orderDTO.getEmail());
//        Order order =foundOrder.orElseThrow(OrderException.ORDER_NOT_FOUND::get);
//        log.info("--- " + order);
//        for (OrderItem orderItem : order.getOrderItems()) {
//
//        }
//        try {
//            order.changeQuantity(orderDTO.getQuantity());
//            OrderItemDTO orderItemDTO = OrderItemDTO.builder().productId(order.getProduct().getPid())
//                    .quantity(order.getQuantity())
//                    .price(order.getPrice())
//                    .category(String.valueOf(order.getCategory()))
//                    .build();
//            if (order.getQuantity() <= 0) {
//                orderItemRepository.delete(order);
//            }
//            return orderItemDTO;
//        }catch (Exception e){
//            log.error(e.getMessage());
//            throw OrderException.NOT_MODIFIED.get();
//        }
        return null;
    }

    //주문 아이템 모두 삭제
    public void deleteAll(String email) {
        Optional<Order> foundEmail = orderRepository.findByEmail(email);
        Order order = foundEmail.orElseThrow(OrderException.ORDER_NOT_FOUND::get);
        try {
            List<OrderItem> byEmails = orderRepository.findByEmails(email).get();
            for (OrderItem byEmail : byEmails) {
                orderItemRepository.delete(byEmail);
            }
            order.getOrderItems().removeAll(order.getOrderItems());
        }catch (Exception e){
            log.error(e.getMessage());
            throw OrderException.NOT_MODIFIED.get();
        }
    }
    
    //오후 2
    @Scheduled(cron = "0 0 14 * * *")
    @Transactional
    public void time() {
        LocalDateTime now = LocalDateTime.now();
        //14시 오후 2시 접수마감 시간
        LocalDateTime cutOffDateTime = now.with(LocalTime.of(14,0));
        // 모든 주문들을 받아오고
        List<Order> all = orderRepository.findAll();
        //주문중에서 14시 전에 주문한 주문들을 모두 준비중으로 바꾼다
        all.stream().filter(order -> order.getCreatedAt().isBefore(cutOffDateTime)).collect(Collectors.toList());
        for (Order order : all) {
            order.changeOrderStatus(OrderStatus.READY_FOR_DELIVERY);
        }
    }
}
