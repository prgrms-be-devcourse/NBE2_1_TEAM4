package com.example.gccoffee.dto;

import com.example.coffee.entity.OrderItem;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Data
public class OrderItemDTO {

    private Long productId;
    private String category;
    private int price;
    @NotBlank
    @Max(99)
    private int quantity;

    public OrderItemDTO(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getPid();
        this.category = String.valueOf(orderItem.getCategory());
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }
}
