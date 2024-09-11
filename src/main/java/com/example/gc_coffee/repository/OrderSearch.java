package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberEmail; // 회원 이메일
    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]
}
