package com.sparta.java_02.domain.purchase.dto;


import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseRequest {

  @NotNull
  Long userId;

  @NotNull
  List<PurchaseProductRequest> purchaseProducts;

}
