package com.sparta.msa_exam.order.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long product_id;
    private String name;
    private Integer supply_price;
}
