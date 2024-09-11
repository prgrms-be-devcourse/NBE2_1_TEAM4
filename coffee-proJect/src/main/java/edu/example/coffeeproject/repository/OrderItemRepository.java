package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
