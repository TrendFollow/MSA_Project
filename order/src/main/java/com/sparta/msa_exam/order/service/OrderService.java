package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClinet;
import com.sparta.msa_exam.order.dto.OrderDto;
import com.sparta.msa_exam.order.dto.OrderProductDto;
import com.sparta.msa_exam.order.dto.ProductDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.exception.OrderFailedException;
import com.sparta.msa_exam.order.repository.OrderProductRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClinet productClinet;
    private final OrderProductRepository orderProductRepository;

    // 주문 생성
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order(orderDto.getName());
        return OrderDto.toDto(orderRepository.save(order));
    }

    // 주문에 상품 추가
    @Transactional
    public OrderProductDto putOrder(Long orderId, ProductDto productDto) {
        Long product_id = productClinet.readOne(productDto.getProduct_id());

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderFailedException("Order not found"));
        OrderProduct order_product = new OrderProduct(order, product_id);

        return OrderProductDto.toDto(orderProductRepository.save(order_product));
    }

    // 주문 단건 조회
    public OrderDto readOne(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderFailedException("Order not found"));
        return OrderDto.toDto(order);
    }
}
