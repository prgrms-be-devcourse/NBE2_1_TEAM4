package com.example.gccoffee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue
    private UUID orderId;
    
    @Embedded
    private Email email; // 얘가 문제를 일으킴

    //private String email;

    private String address;

    private String postcode;

    // order 가 외래키를 갖고있고, 데이터 무결성을 위하며 , orderItem 에 접근했을 때만 로드하도록
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 필요한 필드를 포함한 생성자 추가
    public Order(Email email, String address, String postcode, List<OrderItem> orderItems) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
        this.orderStatus = OrderStatus.ACCEPTED;  // 초기 상태
    }

//    // 필요한 필드를 포함한 생성자 추가
//    public Order(String email, String address, String postcode, List<OrderItem> orderItems) {
//        this.email = email;
//        this.address = address;
//        this.postcode = postcode;
//        this.orderItems = orderItems;
//        this.orderStatus = OrderStatus.ACCEPTED;  // 초기 상태
//    }
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
