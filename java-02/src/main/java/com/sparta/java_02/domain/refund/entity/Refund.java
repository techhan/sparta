package com.sparta.java_02.domain.refund.entity;

import com.sparta.java_02.common.enums.RefundStatus;
import com.sparta.java_02.domain.purchase.entity.Purchase;
import com.sparta.java_02.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "purchase_id", nullable = false)
  private Purchase purchase;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "refund", cascade = CascadeType.ALL, orphanRemoval = true)
  List<RefundItem> items = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RefundStatus status;

  @Column(nullable = false)
  private String reason;

  @Column(precision = 15, scale = 2)
  private BigDecimal totalPrice;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public void setTotalPrice(BigDecimal refundTotalPrice) {
  }
}
