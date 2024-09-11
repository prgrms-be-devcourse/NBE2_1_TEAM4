package com.example.gccoffee.repositroy;


import com.example.coffee.entity.Order;
import com.example.coffee.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {



    @Query(" SELECT o FROM OrderItem o JOIN FETCH o.product " +
            "WHERE o.order.email = :email ORDER BY o.product.pid DESC ")
    Optional<List<OrderItem>> findByEmails(@Param("email") String email);

    Optional<Order> findByEmail(@Param("email")String email);
}
