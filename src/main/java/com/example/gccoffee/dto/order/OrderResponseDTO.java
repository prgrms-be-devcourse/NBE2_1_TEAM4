package com.example.gccoffee.dto.order;

import com.example.gccoffee.entity.Order;
import com.example.gccoffee.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private String email;
    private String address;
    private int postcode;
    private List<OrderItemResponseDTO> orderItems;
    private OrderStatus orderStatus;

    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();
        this.orderStatus = OrderStatus.ACCEPTED;
        this.orderItems = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> this.orderItems.add(new OrderItemResponseDTO(orderItem)));
    }

    public Order toEntity() {
        Order order = Order.builder().orderId(orderId).email(email).address(address).postcode(postcode).orderStatus(OrderStatus.ACCEPTED).build();
        return order;
    }
}
