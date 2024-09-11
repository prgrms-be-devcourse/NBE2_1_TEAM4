package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Order;
import com.example.gccoffee.entity.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // 모든 주문 조회
    public List<Order> findAll() {
        String jpql = "SELECT o FROM Order o";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        return query.getResultList();
    }

    public Order insert(Order order) {
        entityManager.persist(order);
        return order;
    }

    public Order update(Order order) {
        return entityManager.merge(order);
    }

    public Order findById(UUID orderId) {
        return entityManager.find(Order.class, orderId);
    }

    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        String jpql = "SELECT o FROM Order o WHERE o.orderStatus = :orderStatus";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        query.setParameter("orderStatus", orderStatus);
        return query.getResultList();
    }

    // 이메일로 주문 조회
    public List<Order> findByEmail(String email) {
        String jpql = "SELECT o FROM Order o WHERE o.email = :email";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    // 모든 주문 삭제
    public void deleteAll() {
        String jpql = "DELETE FROM Order";
        entityManager.createQuery(jpql).executeUpdate();
    }
}
// 확인용 주석
