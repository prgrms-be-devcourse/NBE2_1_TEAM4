package com.example.gccoffee.repository;

import com.example.gccoffee.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberEmail; // 회원 이메일
    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]
}
