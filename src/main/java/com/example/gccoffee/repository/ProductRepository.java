package com.example.gccoffee.repository;

import com.example.gccoffee.entity.product.Product;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductRepository {

    private final EntityManager em;

    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Product product) {
        if (product.getProductId() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }

    public void delete(Product product) {
        if (em.contains(product)) {
            em.remove(product);
        } else {
            em.remove(em.merge(product));
        }
    }
}
