package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Order;
import com.example.gccoffee.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByEmail(String email);
}
