package com.sparta.java_02.domain.refund.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundItemRequest {

  Long purchaseProductId;
  int refundQuantity;
}
