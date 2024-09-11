package com.example.gccoffee.dto;

import com.example.coffee.entity.Order;
import com.example.coffee.entity.OrderItem;
import com.example.coffee.entity.OrderStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {

    private Long oId;

    @Email
    private String email;

    private String address;

    private Long postcode;

    private List<OrderItem> orderItems;

    private OrderStatus orderStatus;


    @Max(99)
    private int quantity;

    public Order toEntity(OrderDTO orderDTO) {
        Order order = Order.builder().address(orderDTO.getAddress())
                .email(orderDTO.getEmail())
                .postcode(orderDTO.postcode)
                .orderStatus(OrderStatus.ACCEPTED)
                .orderItems(orderDTO.orderItems)
                .build();
        return order;
    }
     public OrderDTO(Order order) {
        this.oId = order.getOid();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();
        this.email = order.getEmail();
     }
     public Map<String, Object> getPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("address", address);
        payload.put("postcode", postcode);
        return payload;
     }
}
