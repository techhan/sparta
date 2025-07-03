package com.sparta.java_02.domain.purchase.controller.dto;

public class PurchaseProductRequestTest {

  private Long productId;
  private int quantity;


  public PurchaseProductRequestTest(Long productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
