package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name"})
public class OrderDto {

    private Long order_id;

    @NotBlank(message = "주문 요청을 작성해주세요.")
    private String name;

    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrder_id(order.getId());
        orderDto.setName(order.getName());
        return orderDto;
    }
}
