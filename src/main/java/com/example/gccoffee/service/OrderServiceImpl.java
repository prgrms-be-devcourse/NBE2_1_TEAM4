package com.example.gccoffee.service;

import com.example.gccoffee.entity.Order;
import com.example.gccoffee.repository.OrderRepository;
import com.example.gccoffee.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}