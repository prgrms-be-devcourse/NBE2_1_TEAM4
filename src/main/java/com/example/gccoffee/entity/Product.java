package com.example.gccoffee.entity;


import com.example.coffee.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Embeddable
@Builder
@ToString
@Table(name = "gc-Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id")
    private Long pid;

    @Column(name = "product_Name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;



    public Product toEntity(ProductDTO productDTO) {
        return Product.builder().pid(pid).name(name)
                .price(price).category(category)
                .description(description).build();
    }

    // change 메서드
    public void changePrice(int price) {
        this.price = price;
    }

    public void changeCategory(String category) {
        this.category = Category.valueOf(category);
    }

    public void changePname(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

}
