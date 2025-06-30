package com.sparta.java_02.domain.product.service;

import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.domain.product.dto.ProductRequest;
import com.sparta.java_02.domain.product.dto.ProductResponse;
import com.sparta.java_02.domain.product.entity.Product;
import com.sparta.java_02.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAll() {
        return new ArrayList<>();
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_USER));

        return ProductResponse.builder()
                .id(product.getId())
                .categoryId(product.getCategoryId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public ProductResponse create(ProductRequest request) {
        return null;
    }

    public ProductResponse update(Long id, ProductRequest request) {
        return null;
    }

    public void delete(Long id) {

    }
}
