package com.example.gccoffee.controller;

import com.example.gccoffee.dto.order.OrderResponseDTO;
import com.example.gccoffee.dto.page.PageRequestDTO;
import com.example.gccoffee.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Log4j2
@Tag(name = "주문", description = "주문 CRUD")
public class OrderController {
    private final OrderService orderService;

    //주문 등록
    @PostMapping
    @Operation(summary = "주문 등록", description = "상품을 주문할 때 사용하는 API")
    public ResponseEntity<OrderResponseDTO> register(@RequestBody OrderResponseDTO orderResponseDTO) {
        log.info("Creating order: {}", orderResponseDTO);
        return ResponseEntity.ok(orderService.create(orderResponseDTO));
    }

    //주문 1개 조회
    @GetMapping("/id/{orderId}")
    @Operation(summary = "주문 조회", description = "1개의 주문을 조회할 때 사용하는 API")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId) {
        log.info("Get order: {}", orderId);
        return ResponseEntity.ok(orderService.readOrderById(orderId));
    }

    //이메일 별 주문 조회
    @GetMapping("/email/{email}")
    @Operation(summary = "이메일 별 주문 조회", description = "이메일 별 주문을 조회할 때 사용하는 API")
    public ResponseEntity<List<OrderResponseDTO>> getOrder(@PathVariable String email) {
        log.info("Get order: {}", email);
        return ResponseEntity.ok(orderService.readOrderByEmail(email));
    }

    //주문 수정
    @PutMapping("/{orderId}")
    @Operation(summary = "주문 수정", description = "주문을 수정할 때 사용하는 API")
    public ResponseEntity<OrderResponseDTO> modifyOrder(@PathVariable Long orderId, @RequestBody OrderResponseDTO orderResponseDTO) {
        log.info("Update order: {}", orderResponseDTO);
        return ResponseEntity.ok(orderService.update(orderId, orderResponseDTO));
    }

    //주문 삭제
    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 삭제", description = "주문을 삭제할 때 사용하는 API")
    public ResponseEntity<Map<String, String>> removeOrder(@PathVariable Long orderId) {
        log.info("Remove order: {}", orderId);
        orderService.delete(orderId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }

    //주문 리스트
    @GetMapping
    @Operation(summary = "주문 리스트 조회", description = "주문 리스트를 조회할 때 사용하는 API")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(@RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "size", defaultValue = "5") int size) {

        log.info("Get all orders");

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(size).build();

        return ResponseEntity.ok(orderService.readAll(pageRequestDTO));
    }
}
