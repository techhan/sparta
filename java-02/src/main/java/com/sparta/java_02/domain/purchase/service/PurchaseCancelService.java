package com.sparta.java_02.domain.purchase.service;

import com.sparta.java_02.common.constants.Constants;
import com.sparta.java_02.common.enums.PurchaseStatus;
import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.domain.product.entity.Product;
import com.sparta.java_02.domain.purchase.dto.PurchaseCancelResponse;
import com.sparta.java_02.domain.purchase.dto.PurchaseProductResponse;
import com.sparta.java_02.domain.purchase.entity.Purchase;
import com.sparta.java_02.domain.purchase.entity.PurchaseProduct;
import com.sparta.java_02.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.java_02.domain.purchase.repository.PurchaseRepository;
import com.sparta.java_02.domain.user.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseCancelService {

  private final UserRepository userRepository;
  private final PurchaseRepository purchaseRepository;
  private final PurchaseProductRepository purchaseProductRepository;

  @Transactional
  public PurchaseCancelResponse cancel(Long purchaseId, Long userId) {
    Purchase purchase = purchaseRepository.findByIdAndUserId(purchaseId, userId)
        .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_DATA));

    validatePurchaseStatus(purchase);
    purchase.setStatus(PurchaseStatus.CANCELED);

    // 구매 목록 가져오기
    List<PurchaseProduct> purchaseProducts = purchaseProductRepository.findByPurchaseId(
        purchase.getId());
    restoreProductStock(purchaseProducts); // 재고 복원

    // 결제 취소 API
    // 주문 취소 알람

    List<PurchaseProductResponse> purchaseProductResponses = getPurchaseProductResponses(
        purchaseProducts);

    return PurchaseCancelResponse.builder()
        .purchaseId(purchase.getId())
        .purchaseStatus(purchase.getStatus())
        .cancelledAt(LocalDateTime.now())
        .message(Constants.PURCHASE_CANCEL_MESSAGE)
        .cancelledProducts(purchaseProductResponses)
        .build();
  }

  private List<PurchaseProductResponse> getPurchaseProductResponses(
      List<PurchaseProduct> purchaseProducts) {
    return purchaseProducts.stream()
        .map((purchaseProduct) -> {
          Product product = purchaseProduct.getProduct();
          BigDecimal totalPrice = purchaseProduct.getPrice()
              .multiply(BigDecimal.valueOf(purchaseProduct.getQuantity()));

          return PurchaseProductResponse.builder()
              .productId(product.getId())
              .productName(product.getName())
              .quantity(purchaseProduct.getQuantity())
              .totalPrice(totalPrice)
              .build();
        })
        .toList();
  }

  private void restoreProductStock(List<PurchaseProduct> purchaseProducts) {
    for (PurchaseProduct purchaseProduct : purchaseProducts) {
      Product product = purchaseProduct.getProduct();
      product.increaseStock(purchaseProduct.getQuantity());
    }
  }

  private void validatePurchaseStatus(Purchase purchase) {
    // 취소가 가능한 상태인지 확인하는 로직도 필요함 ex) 주문 완료 상태에는 취소 못함... 환불로 해야함
    if (purchase.getStatus() != PurchaseStatus.PENDING) {
      throw new ServiceException(ServiceExceptionCode.CANNOT_CANCEL);
    }
  }
}
