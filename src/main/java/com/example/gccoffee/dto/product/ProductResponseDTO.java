package com.example.gccoffee.dto.product;

import com.example.gccoffee.entity.Category;
import com.example.gccoffee.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDTO {
    private Long productId;

    @NotBlank
    private String productName;

    private Category category;

    @Min(0)
    private int price;

    private String description;

    public ProductResponseDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    public Product toEntity() {
        Product product = Product.builder().productId(productId).productName(productName).category(category).price(price).description(description).build();
        return product;
    }
}
