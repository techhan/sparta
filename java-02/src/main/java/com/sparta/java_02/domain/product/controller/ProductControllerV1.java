package com.sparta.java_02.domain.product.controller;

import com.sparta.java_02.domain.product.dto.ProductRequest;
import com.sparta.java_02.domain.product.dto.ProductResponse;
import com.sparta.java_02.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductControllerV1 {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<String> findProduct() {
        return ResponseEntity.ok("Hello world");
    }

    // 전체상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    // 단일 상품 조회
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    // 상품 생성
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest reqeust) {
        // 201 Created 상태 코드와 함께 응답
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(request));
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        // 204 No Content 상태 코드로 응답
        return ResponseEntity.noContent().build();
    }
}
