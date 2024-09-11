package com.example.gccoffee.entity;

import com.example.gccoffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final OrderService orderService;

    @Scheduled(cron = "0 0 14 * * *") // 매일 오후 2시에 실행
    public void processOrders() {
        orderService.processOrdersForPeriod();
    }
}
