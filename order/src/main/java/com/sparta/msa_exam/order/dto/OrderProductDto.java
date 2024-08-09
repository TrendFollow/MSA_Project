package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.OrderProduct;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"order_product_id","product_id"})
public class OrderProductDto {

    private Long order_product_id;
    private OrderDto orderDto;
    private Long product_id;

    public static OrderProductDto toDto(OrderProduct orderProduct) {
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setOrder_product_id(orderProduct.getProductId());
        orderProductDto.setProduct_id(orderProduct.getProductId());
        orderProductDto.setOrderDto(OrderDto.toDto(orderProduct.getOrder()));
        return orderProductDto;

    }
}
