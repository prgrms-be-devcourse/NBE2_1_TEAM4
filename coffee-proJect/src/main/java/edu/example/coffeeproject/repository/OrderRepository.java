package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByEmail(String email);

    void deleteByEmail(String email);

    List<Order> findByOrderStatus (OrderStatus orderStatus);
}
