package com.example.gccoffee.controller;

import com.example.gccoffee.entity.Category;

public record CreateProductRequest(String productName, Category category,long price, String description) {
}
