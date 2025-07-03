package com.sparta.java_02.domain.refund.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundRequest {

  Long purchaseId;
  Long userId;
  String reason;
  List<RefundItemRequest> items;
}
