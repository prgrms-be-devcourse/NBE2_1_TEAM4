package com.example.gccoffee.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String address;

    private int postcode;

    @NotEmpty
    private List<OrderItemRequestDTO> orderItems;
}
