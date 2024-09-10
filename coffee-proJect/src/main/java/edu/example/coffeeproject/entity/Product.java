package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changeCategory(Category category) {
        this.category = category;
        }
    public void changePrice(int price) {
        this.price = price;
    }
    public void changeDescription(String description) {
        this.description = description;
    }
}
