package com.example.gc_coffee.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private String email; // 이메일
    private String address; // 주소
    private String postcode; // 우편번호
    private List<OrderItemDto> orderItems; // 주문 항목 리스트

    @Getter
    @Setter
    public static class OrderItemDto {
        private Long productId; // 제품 ID
        private int quantity; // 수량
    }
}
