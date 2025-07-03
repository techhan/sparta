package com.sparta.java_02.domain.purchase.dto;

import com.sparta.java_02.common.enums.PurchaseStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseCancelResponse {

  Long purchaseId;
  PurchaseStatus purchaseStatus;
  LocalDateTime cancelledAt;
  String message;
  List<PurchaseProductResponse> cancelledProducts;
}
