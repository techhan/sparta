package com.sparta.java_02.domain.purchase.entity;

import com.sparta.java_02.common.enums.PurchaseStatus;
import com.sparta.java_02.domain.user.entity.User;
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
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Purchase { // 주문

  @Builder
  public Purchase(User user, BigDecimal totalPrice, PurchaseStatus status) {
    this.user = user;
    this.totalPrice = totalPrice;
    this.status = status;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PurchaseStatus status;
  // 코드를 만드는 주체가 개발자면 Enum으로 만들어도 됨
  // 관리자 페이지에서 코드를 생성하는 행위 등은 DB 상에서 관리해야됨

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  private LocalDateTime updateAt;
}
