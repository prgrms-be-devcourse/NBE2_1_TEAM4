package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private Category category;
    private int price;
    private int quantity;

    public OrderItemDTO(OrderItem orderItem) {
        productId = orderItem.getProduct().getProductId();
        category = orderItem.getProduct().getCategory();
        price = orderItem.getProduct().getPrice();
        quantity = orderItem.getQuantity();
    }
}
