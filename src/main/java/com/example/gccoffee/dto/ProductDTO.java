package com.example.gccoffee.dto;


import com.example.coffee.entity.Category;
import com.example.coffee.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    @NotBlank(message = "상품명은 비어있을 수 없습니다")
    private String product_name;

    @Range(min = 100, max =  1000000)
    private int price;

    @NotBlank(message = "설명은 비어있을 수 없습니다")
    private String description;

    @NotBlank(message = "카테고리를 선택해주세요")
    private String category;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;


    public ProductDTO(Product product) {
        this.productId =product.getPid();
        this.product_name=product.getName();
        this.price=product.getPrice();
        this.description=product.getDescription();
        this.category= String.valueOf(product.getCategory());
        this.created_at=product.getCreatedAt();
        this.updated_at=product.getUpdatedAt();
    }

    public Product toEntity(){
        Product product = Product.builder().pid(productId)
                .price(price).name(product_name)
                .category(Category.valueOf(category))
                .description(description).build();
        return product;
    }
}
