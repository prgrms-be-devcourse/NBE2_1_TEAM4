package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
@Table(name = "orderItems_tbl")
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)

    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int price;
    private int quantity;
}
