package edu.example.coffeeproject.dto;

import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.OrderStatus;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.OrderException;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Log4j2
@Data
@NoArgsConstructor
public class OrderDTO {
    private String email;
    private String address;
    private String postCode;
    private List<OrderItemDTO> orderItems;
    private OrderStatus orderStatus;

    public OrderDTO(Order order) {
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postCode = order.getPostCode();
        this.orderStatus = order.getOrderStatus();
        this.orderItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            this.orderItems.add(new OrderItemDTO(orderItem));
        }
    }

    public Order toEntity(ProductRepository productRepository) {

        List<OrderItem> orderItemEntitys = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItems) {

            Product product = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(OrderException.NOT_FOUND_ORDER::get);
            Order order = Order.builder().orderId(1L).build();

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .category(orderItemDTO.getCategory())
                    .price(orderItemDTO.getPrice())
                    .quantity(orderItemDTO.getQuantity())
                    .build();
            orderItemEntitys.add(orderItem);
        }

        return Order.builder()
                .email(email)
                .address(address)
                .postCode(postCode)
                .orderStatus(orderStatus)
                .orderItems(orderItemEntitys)  // OrderItem 리스트 추가
                .build();
    }
}
