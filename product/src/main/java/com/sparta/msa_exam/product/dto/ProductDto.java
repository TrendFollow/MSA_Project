package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "supply_price"})
public class ProductDto implements Serializable {

    private Long product_id;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @NotNull(message = "상품 가격은 필수입니다.")
    @Min(value = 1, message = "최소 가격은 1이상이어야 합니다.")
    private Integer supply_price;


    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProduct_id(product.getId());
        productDto.setName(product.getName());
        productDto.setSupply_price(product.getSupplyPrice());
        return productDto;
    }

    public ProductDto(Product product) {
        this.product_id = product.getId();
        this.name = product.getName();
        this.supply_price = product.getSupplyPrice();
    }
}
