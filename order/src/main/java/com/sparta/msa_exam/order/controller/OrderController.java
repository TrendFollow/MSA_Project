package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderDto;
import com.sparta.msa_exam.order.dto.OrderProductDto;
import com.sparta.msa_exam.order.dto.ProductDto;
import com.sparta.msa_exam.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private HttpHeaders headers = new HttpHeaders();

    @Value("${server.port}")
    private String port;

    // 주문 생성
    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto) {
        log.info("주문 생성 중 : {}", orderDto.toString());
        headers.add("Server-Port", port);

        OrderDto result = orderService.createOrder(orderDto);

        log.info("주문 생성 완료: {}", result.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
    }

    // 주문에 상품 추가
    @PutMapping("/order/{orderId}")
    public ResponseEntity<?> putOrder(@PathVariable("orderId") Long orderId, @Valid @RequestBody ProductDto productDto) {
        log.info("주문에 상품 추가 중: {}", productDto.getProduct_id());
        headers.add("Server-Port", port);

        OrderProductDto result = orderService.putOrder(orderId, productDto);
        log.info("주문에 상품 추가 성공: {}", result.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
    }

    // 주문 단건 조회
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> readOne(@PathVariable("orderId") Long orderId) {
        log.info("주문 단건 조회 중 : {}", orderId);
        headers.add("Server-Port", port);

        OrderDto result = orderService.readOne(orderId);
        log.info("주문 단건 조회 성공: {}", result.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
    }
}
