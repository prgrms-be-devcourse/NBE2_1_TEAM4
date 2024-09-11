package com.example.gccoffee.controller;


import com.example.coffee.dto.OrderDTO;
import com.example.coffee.security.util.JWTUtil;
import com.example.coffee.service.OrderService;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/v1/token/")
@RequiredArgsConstructor
public class TokenController {
    private final JWTUtil jwtUtil;
    private final OrderService orderService;
    private final DefaultSslBundleRegistry sslBundleRegistry;


    @PostMapping("/make")
    public ResponseEntity<?> makeToken(@RequestBody OrderDTO orderDTO) {

        log.info("Making token");
        OrderDTO foundOrderDTO = orderService.findByEmail(orderDTO.getEmail());

        Map<String, Object> payload = orderDTO.getPayload();

        String accessToken = jwtUtil.createToken(payload, 60 * 24 * 30);
        String refreshToken = jwtUtil.createToken(Map.of("email", foundOrderDTO.getEmail()), 60 * 24 * 30);
        log.info("--- access token: " + accessToken);

        return ResponseEntity.ok(Map.of("access_token", accessToken, "refresh_token", refreshToken));
    }

}
