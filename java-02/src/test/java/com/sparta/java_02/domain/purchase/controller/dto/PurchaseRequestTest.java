package com.sparta.java_02.domain.purchase.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PurchaseRequestTest {

  @NotNull
  Long userId;

  @NotNull
  List<PurchaseProductRequestTest> purchaseProducts;

  public PurchaseRequestTest(Long userId, List<PurchaseProductRequestTest> purchaseProducts) {
    this.userId = userId;
    this.purchaseProducts = purchaseProducts;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public List<PurchaseProductRequestTest> getPurchaseProducts() {
    return purchaseProducts;
  }

  public void setPurchaseProducts(
      List<PurchaseProductRequestTest> purchaseProducts) {
    this.purchaseProducts = purchaseProducts;
  }
}
