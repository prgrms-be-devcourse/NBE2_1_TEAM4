package com.example.gccoffee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.gccoffee.entity.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private long orderPrice;
    private int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Product product, long orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        product.removeStock(count); // 주문 시 재고 감소
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        if (product != null) {
            product.addStock(count); // 취소 시 재고 복원
        }
    }

    //==조회 로직==//
    public long getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
