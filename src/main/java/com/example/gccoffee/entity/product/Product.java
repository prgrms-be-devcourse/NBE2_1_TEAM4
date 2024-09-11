package com.example.gccoffee.entity.product;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment strategy for Long IDs
    @Column(name = "product_id")
    private Long productId;

    private String productName;

    @Enumerated(EnumType.STRING)
    private CoffeeType type; // 커피 종류를 나타내는 enum 필드

    private long price;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int stockQuantity; // 재고 수량 추가

    // Default constructor
    public Product() {}

    // Parameterized constructor
    public Product(Long productId, String productName, CoffeeType type, long price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(Long productId, String productName, CoffeeType type, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        updateTimestamp();
    }

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
        updateTimestamp();
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    //==비즈니스 로직==//
    /**
     * 재고 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalArgumentException("Not enough stock");
        }
        this.stockQuantity = restStock;
    }
}
