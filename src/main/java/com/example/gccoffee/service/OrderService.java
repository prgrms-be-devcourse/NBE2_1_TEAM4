package com.example.gccoffee.service;

import com.example.gccoffee.dto.order.OrderItemResponseDTO;
import com.example.gccoffee.dto.order.OrderResponseDTO;
import com.example.gccoffee.entity.Order;
import com.example.gccoffee.entity.OrderItem;
import com.example.gccoffee.entity.OrderStatus;
import com.example.gccoffee.entity.Product;
import com.example.gccoffee.exception.OrderException;
import com.example.gccoffee.exception.ProductException;
import com.example.gccoffee.repository.OrderRepository;
import com.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponseDTO create(OrderResponseDTO orderResponseDTO) {
        try {
            Order order = orderResponseDTO.toEntity();

            List<OrderItem> orderItems = new ArrayList<>();

            for (OrderItemResponseDTO orderItemResponseDTO : orderResponseDTO.getOrderItems()) {
                Product product = productRepository.findById(orderItemResponseDTO.getProductId()).orElseThrow(ProductException.NOT_FOUND::get);

                OrderItem orderItem = OrderItem.builder()
                        .product(product)
                        .order(order)
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .quantity(orderItemResponseDTO.getQuantity())
                        .build();

                orderItems.add(orderItem);
            }

            order.updateOrderItems(orderItems);

            orderRepository.save(order);

            return new OrderResponseDTO(order);
        } catch (Exception e) {
            log.error(e);
            throw OrderException.NOT_REGISTERED.get();
        }
    }

    //주문 1개 조회
    public OrderResponseDTO readOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.NOT_FOUND::get);

        return new OrderResponseDTO(order);
    }

    //이메일 별 주문 조회
    public List<OrderResponseDTO> readOrderByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();

        for (Order order : orders) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);
            orderResponseDTOs.add(orderResponseDTO);
        }

        return orderResponseDTOs;
    }

    //주문 수정
    public OrderResponseDTO update(Long id, OrderResponseDTO orderResponseDTO) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.NOT_FOUND::get);

        try {
            order.changeAddress(orderResponseDTO.getAddress());
            order.changePostcode(orderResponseDTO.getPostcode());

            return new OrderResponseDTO(order);
        } catch (Exception e) {
            log.error(e);
            throw OrderException.NOT_MODIFIED.get();
        }
    }

    //주문 삭제
    public OrderResponseDTO delete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.NOT_FOUND::get);

        try {
            orderRepository.delete(order);

            return new OrderResponseDTO(order);
        } catch (Exception e) {
            log.error(e);
            throw OrderException.NOT_REMOVED.get();
        }
    }

    //주문 리스트
    public List<OrderResponseDTO> readAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();

        for (Order order : orders) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);
            orderResponseDTOs.add(orderResponseDTO);
        }

        return orderResponseDTOs;
    }

    //매일 14시에 주문 상태 변경
    @Scheduled(cron = "0 14 00 * * ?")
    public void updateOrderStatus() {
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (order.getOrderStatus() != OrderStatus.SETTLED && order.getOrderStatus() != OrderStatus.CANCELLED) {
                order.changeOrderStatus(changeStatus(order.getOrderStatus()));
                orderRepository.save(order);
            }
            else if (order.getOrderStatus() == OrderStatus.SETTLED || order.getOrderStatus() == OrderStatus.CANCELLED) {
                orderRepository.delete(order);
            }
        }
    }

    private OrderStatus changeStatus(OrderStatus status) {
        switch (status) {
            case ACCEPTED:
                return OrderStatus.PAYMENT_CONFIRMED;
            case PAYMENT_CONFIRMED:
                return OrderStatus.READY_FOR_DELIVERY;
            case READY_FOR_DELIVERY:
                return OrderStatus.SHIPPED;
            case SHIPPED:
                return OrderStatus.SETTLED;
            default:
                return status;
        }
    }
}

