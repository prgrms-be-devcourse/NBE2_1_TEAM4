package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    public Order findByEmail(String emailAddress) {
        TypedQuery<Order> query = em.createQuery(
                "select o from Order o where o.email.address = :emailAddress", Order.class
        );
        query.setParameter("emailAddress", emailAddress);
        List<Order> orders = query.getResultList();

        if (orders.isEmpty()) {
            return null;
        } else if (orders.size() > 1) {
            throw new IllegalStateException("Duplicate orders found with the same email address.");
        } else {
            return orders.get(0);
        }
    }

    public List<Order> findOrdersBetween(LocalDateTime start, LocalDateTime end) {
        TypedQuery<Order> query = em.createQuery(
                "select o from Order o where o.orderDate between :start and :end", Order.class
        );
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }
}
