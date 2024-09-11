package com.example.gccoffee.controller.api;

import com.example.coffee.entity.OrderItem;

import java.util.List;

public record CreateOrderRequest(String email, String address, int postcode, List<OrderItem> orderItems) {

}
