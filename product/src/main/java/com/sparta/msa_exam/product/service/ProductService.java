package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.exception.ProductFailedException;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
    @Transactional
    @CachePut(cacheNames = "createCache", key = "#result.product_id")
    @CacheEvict(cacheNames = "findAll", allEntries = true)
    public ProductDto createProduct(ProductDto productDto) {

        Product product = new Product(productDto.getName(), productDto.getSupply_price());

        return ProductDto.toDto(productRepository.save(product));
    }

    // 상품 전체 목록 조회
    @Cacheable(cacheNames = "findAll", key = "getMethodName()")
    public List<ProductDto> findAllProducts() {
        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    // 상품 단건 조회
    public Long readOne(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductFailedException("조회한 상품이 존재하지 않습니다."));
        return product.getId();
    }
}
