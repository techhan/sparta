package com.sparta.java_02.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    Long categoryId;

    @NotNull
    String name;

    String description;

    @NotNull
    @Positive
    BigDecimal price;

    @NotNull
    @PositiveOrZero
    Integer stock;
}
