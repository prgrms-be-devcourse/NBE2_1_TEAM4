package com.example.gccoffee.dto.order;

import com.example.gccoffee.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long productId;
    private String category;
    private int price;
    private int quantity;

    public OrderItemResponseDTO(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getProductId();
        this.category = orderItem.getProduct().getCategory().name();
        this.price = orderItem.getProduct().getPrice();
        this.quantity = orderItem.getQuantity();
    }
}
