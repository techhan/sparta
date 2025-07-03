package com.sparta.java_02.domain.refund.entity;

import com.sparta.java_02.domain.purchase.entity.PurchaseProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "refund_id", nullable = false)
  private Refund refund;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "purchase_product_id", nullable = false)
  private PurchaseProduct purchaseProduct;

  @Column(nullable = false)
  private int refundQuantity;

  @Column(precision = 15, scale = 2, nullable = false)
  private BigDecimal refundAmount;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Builder
  public RefundItem(Refund refund, PurchaseProduct purchaseProduct, int refundQuantity,
      BigDecimal refundAmount) {
    this.refund = refund;
    this.purchaseProduct = purchaseProduct;
    this.refundQuantity = refundQuantity;
    this.refundAmount = refundAmount;
  }
}
