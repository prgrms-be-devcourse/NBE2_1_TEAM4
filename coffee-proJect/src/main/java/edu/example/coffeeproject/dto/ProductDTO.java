package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Category;
import edu.example.coffeeproject.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Category category;
    private int Price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.Price = product.getPrice();
        this.description = product.getDescription();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    public Product toEntity(){
        Product product = Product.builder()
                .productId(productId)
                .productName(productName)
                .category(category)
                .price(Price)
                .description(description)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        return product;
    }
}
