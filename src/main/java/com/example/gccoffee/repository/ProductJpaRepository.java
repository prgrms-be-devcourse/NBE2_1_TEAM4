package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Category;
import com.example.gccoffee.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // 모든 상품 조회
    public List<Product> findAll() {
        String jpql = "SELECT p FROM Product p";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        return query.getResultList();
    }

    // 상품 삽입
    public Product insert(Product product) {
        entityManager.persist(product);
        return product;
    }

    // 상품 업데이트
    public Product update(Product product) {
        return entityManager.merge(product);
    }

    // 상품 ID로 조회
    public Optional<Product> findById(UUID productId) {
        Product product = entityManager.find(Product.class, productId);
        return Optional.ofNullable(product);
    }

    // 상품 이름으로 조회
    public Optional<Product> findByProductName(String productName) {
        String jpql = "SELECT p FROM Product p WHERE p.productName = :productName";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("productName", productName);
        return query.getResultList().stream().findFirst();
    }

    // 카테고리로 상품 조회
    public List<Product> findByCategory(Category category) {
        String jpql = "SELECT p FROM Product p WHERE p.category = :category";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    // 모든 상품 삭제
    public void deleteAll() {
        String jpql = "DELETE FROM Product";
        entityManager.createQuery(jpql).executeUpdate();
    }
}
