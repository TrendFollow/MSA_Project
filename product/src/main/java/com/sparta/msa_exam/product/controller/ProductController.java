package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductDto;
import com.sparta.msa_exam.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private HttpHeaders headers = new HttpHeaders();
    private final ProductService productService;

    @Value("${server.port}")
    private String port;


    // 상품 등록
    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProdudct(@Valid @RequestBody ProductDto productDto,
                                                     @RequestHeader(value = "X-Role") String role) {
        log.info("상품 등록 중: {}", productDto.toString());
        if (!"ADMIN".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not ADMIN");
        }

        headers.add("Server-Port", port);

        ProductDto result = productService.createProduct(productDto);
        log.info("상품 등록 완료: {}", result.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
    }

    // 상품 전체 목록 조회
    @GetMapping("/products")
    public ResponseEntity<?> findAllProducts() {
        log.info("전체 상품 목록 조회 중 ~~~~");
        headers.add("Server-Port", port);

        List<ProductDto> result = productService.findAllProducts();

        log.info("전제 상품 목록 조회 완료: {}", result.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
    }

    // 상품 단건 조회 ID 반환
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> readOne(@PathVariable("productId") Long productId) {
        log.info("상품 단건 조회 중: {}", productId);
        headers.add("Server-Port", port);

        Long result = productService.readOne(productId);
        log.info("상품 단건 조회 성공: {}", result);

        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
    }

}
