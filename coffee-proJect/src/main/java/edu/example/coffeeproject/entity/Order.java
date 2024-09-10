package edu.example.coffeeproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "orders")
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String address;

    private String postCode;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePostCode(String postCode) {
        this.postCode = postCode;
    }


    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
