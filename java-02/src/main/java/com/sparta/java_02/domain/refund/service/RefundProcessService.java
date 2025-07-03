package com.sparta.java_02.domain.refund.service;

import com.sparta.java_02.domain.purchase.entity.PurchaseProduct;
import com.sparta.java_02.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.java_02.domain.purchase.repository.PurchaseRepository;
import com.sparta.java_02.domain.refund.dto.RefundItemRequest;
import com.sparta.java_02.domain.refund.dto.RefundRequest;
import com.sparta.java_02.domain.refund.entity.Refund;
import com.sparta.java_02.domain.refund.entity.RefundItem;
import com.sparta.java_02.domain.refund.repository.RefundItemRepository;
import com.sparta.java_02.domain.refund.repository.RefundRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundProcessService {

  private final RefundRepository refundRepository;
  private final PurchaseRepository purchaseRepository;
  private final PurchaseProductRepository purchaseProductRepository;
  private final RefundItemRepository refundItemRepository;

  @Transactional
  public Refund processRefund(Refund refund, RefundRequest request) {
    // 1. 요청 데이터를 다루기 쉬운 Map 형태로 가공한다.
    Map<Long, Integer> refundItemMap = buildRefundItemMap(request);
    List<PurchaseProduct> purchaseProducts = purchaseProductRepository.findAllById(
        refundItemMap.keySet());

    List<RefundItem> refundItems = new ArrayList<>();
    BigDecimal refundTotalPrice = BigDecimal.ZERO;

    // 2. 원본 구매 항목들을 순회하며 환불 처리
    for (PurchaseProduct purchaseProduct : purchaseProducts) {
      int refundQuantity = refundItemMap.get(purchaseProduct.getId());

      // 2-1. 환불 수량 검증
      validateRefundQuantity(purchaseProduct, refundQuantity);

      // 2-2. 원본 구매 항목의 수량 감소 및 상품 재고 복원 (변경 감지)
      purchaseProduct.getProduct().increaseStock(refundQuantity);
      purchaseProduct.decreaseQuantity(refundQuantity);

      // 2-3. 환불 금액 계산 및 환불 아이템 생성
      BigDecimal refundAmount = calculateRefundAmount(purchaseProduct, refundQuantity);
      RefundItem refundItem = buildRefundItem(refund, purchaseProduct, refundQuantity,
          refundAmount);

      refundItems.add(refundItem);
      refundTotalPrice = refundTotalPrice.add(refundAmount);

    }

    // 3. 생성된 환불 아이템들 저장
    refundItemRepository.saveAll(refundItems);

    // 4. 계산된 총 환불 금액을 환불 객체에 설정
    refund.setTotalPrice(refundTotalPrice);
    return refund;

  }

  private RefundItem buildRefundItem(Refund refund, PurchaseProduct purchaseItem,
      int refundQuantity, BigDecimal refundAmount) {
    return RefundItem.builder()
        .refund(refund)
        .purchaseProduct(purchaseItem)
        .refundQuantity(refundQuantity)
        .refundAmount(refundAmount)
        .build();
  }

  private BigDecimal calculateRefundAmount(PurchaseProduct purchaseProduct, int refundQuantity) {
    BigDecimal unitPrice = purchaseProduct.getProduct().getPrice();
    return unitPrice.multiply(BigDecimal.valueOf(refundQuantity));
  }

  private void validateRefundQuantity(PurchaseProduct purchaseProduct, int refundQuantity) {
    if (refundQuantity <= 0) {
      throw new IllegalArgumentException("환불 수량은 1 이상이어야 합니다.");
    }

    if (refundQuantity > purchaseProduct.getQuantity()) {
      throw new IllegalStateException(
          String.format("환불 수량이 구매 수량을 초과했습니다. (상품 ID : %d)", purchaseProduct.getId()));
    }
  }

  private Map<Long, Integer> buildRefundItemMap(RefundRequest request) {
    return request.getItems().stream()
        .collect(Collectors.toMap(
            RefundItemRequest::getPurchaseProductId,
            RefundItemRequest::getRefundQuantity,
            (existing, duplicate) -> {
              throw new IllegalStateException("중복된 상품 환불 요청이 있습니다.");
            }
        ));
  }
}
