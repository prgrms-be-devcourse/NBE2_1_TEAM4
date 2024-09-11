package com.example.gccoffee.controller;


import com.example.coffee.dto.OrderDTO;
import com.example.coffee.dto.OrderItemDTO;
import com.example.coffee.dto.ProductDTO;
import com.example.coffee.entity.Order;
import com.example.coffee.entity.Product;
import com.example.coffee.service.OrderService;
import com.example.coffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;



    //
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@Validated @RequestBody OrderDTO orderDTO) {

        log.info("createOrder : "+orderDTO);
        return ResponseEntity.ok(orderService.add(orderDTO));
    }

    //R
    // email 사용자 정보로 orderItems를 불러와 출력
    @PostMapping()
    public ResponseEntity<List<OrderItemDTO>> ListByEmail(@RequestParam String email) {

        log.info("emailList");

        return ResponseEntity.ok(orderService.findByEmails(email));
    }
    @GetMapping()
    public ResponseEntity<List<OrderDTO>> readAll() {
        List<Order> all = orderService.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : all) {
            orderDTOS.add(new OrderDTO(order));
        }

        return ResponseEntity.ok(orderDTOS);
    }



    //Put 해당하는 id에 해당하는 데이터를 갱신하는 기능을 구현한다.
    //예를들면 id에 해당하는 Item의 개수를 조정한다.. 가격 설명 조정은 x

    //U
    @PutMapping
    public ResponseEntity<OrderDTO> updateOrder(@Validated @RequestBody OrderDTO orderDTO) {

        log.info("updateOrder : "+orderDTO);
        orderService.update(orderDTO);

        return null;
    }

    //D
    // email에 있는 모든 아이템의 주문을 취소
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) {
        orderService.deleteAll(email);
        return ResponseEntity.ok(Map.of("delete","success"));
    }


}
