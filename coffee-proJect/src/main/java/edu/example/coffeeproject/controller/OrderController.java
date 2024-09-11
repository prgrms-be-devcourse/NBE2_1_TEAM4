package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.add(orderDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> read(@PathVariable long id) {
        return ResponseEntity.ok(orderService.read(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, String>> modify(@PathVariable long id, @RequestBody OrderDTO orderDTO) {
        orderService.update(id,orderDTO);
        return ResponseEntity.ok(Map.of("message", "Order modified"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable long id) {
        orderService.remove(id);
        return ResponseEntity.ok(Map.of("message", "Order deleted"));
    }

}
