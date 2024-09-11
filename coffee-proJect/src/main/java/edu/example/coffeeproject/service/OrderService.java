package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.OrderDTO;
import edu.example.coffeeproject.dto.OrderItemDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderStatus;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.OrderException;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderDTO add(OrderDTO orderDTO) {
        try {
            Order order = orderDTO.toEntity(productRepository);
            Order save = orderRepository.save(order);
            return new OrderDTO(save);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw OrderException.NOT_FOUND_ORDER.get();
        }
    }

    public OrderDTO read(Long id){
        Optional<Order> findProduct = orderRepository.findById(id);
        Order order = findProduct.orElseThrow(OrderException.NOT_FOUND_ORDER::get);

        return new OrderDTO(order);
    }

    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.NOT_FOUND_ORDER::get);
        try {
            order.changeAddress(orderDTO.getAddress());
            order.changePostCode(order.getPostCode());
            order.changeOrderStatus(orderDTO.getOrderStatus());

            orderRepository.save(order);
            return new OrderDTO(order);
        }catch (Exception e){
            log.error(e.getMessage());
            throw OrderException.NOT_FOUND_ORDER.get();
        }
    }

    public void remove(Long id){
        Order order = orderRepository.findById(id).orElseThrow(OrderException.NOT_FOUND_ORDER::get);

        try {
            orderRepository.delete(order);
        }catch (Exception e){
            log.error(e.getMessage());
            throw OrderException.NOT_FOUND_ORDER.get();
        }
    }
    @Scheduled(cron = "0 */1 * * * ?")
    public void processOrders() {
        // 처리할 주문 리스트 가져오기 (예: 주문 상태가 ACCEPTED인 주문)
        List<Order> ordersToProcess = orderRepository.findByOrderStatus(OrderStatus.ACCEPTED);

        // 주문 처리 로직
        for (Order order : ordersToProcess) {
            // 예: 주문 상태를 배송 준비 중으로 변경
            order.changeOrderStatus(OrderStatus.READY_FOR_DELIVERY);
            orderRepository.save(order);
        }

        log.info("14시까지의 주문을 일괄 처리했습니다. 총 처리된 주문 수: {}", ordersToProcess.size());
    }
}
