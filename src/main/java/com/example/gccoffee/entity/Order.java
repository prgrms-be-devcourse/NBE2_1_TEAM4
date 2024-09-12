package com.example.gccoffee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private int postcode;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePostcode(int postcode) {
        this.postcode = postcode;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public void updateOrderItems(List<OrderItem> orderItems) { //주문 상품 등록
        this.orderItems = orderItems;
    }

    public void addOrderItems(List<OrderItem> newItems) { //주문 상품 추가
        this.orderItems.addAll(newItems);
        this.updatedAt = LocalDateTime.now();
    }
}
