package com.example.gccoffee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID productId;

    private String productName;

    @Enumerated(EnumType.STRING)
    private Category category;

    private long price;

    private String description;

    @CreatedDate  // 생성 시점
    @Column(updatable = false)  // 수정되지 않도록
    private LocalDateTime createdAt;

    @LastModifiedDate  // 수정 시점
    private LocalDateTime updatedAt;

    public Product(UUID productId, String productName, Category category, long price, String description) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
