package com.sparta.java_02.domain.purchase.repository;

import static com.sparta.java_02.common.enums.PurchaseStatus.PENDING;

import com.sparta.java_02.common.enums.PurchaseStatus;
import com.sparta.java_02.domain.purchase.entity.Purchase;
import com.sparta.java_02.domain.user.entity.User;
import com.sparta.java_02.domain.user.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class PurchaseRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Test
  void 저장() {

    User user = userRepository.save(User.builder()
        .name("d")
        .email("d")
        .passwordHash("a")
        .build());

    Purchase purchase = Purchase.builder()
        .user(user)
        .totalPrice(BigDecimal.valueOf(1000))
        .status(PENDING)
        .build();

    Purchase save = purchaseRepository.save(purchase);
  }

  @Test
  void 수정() {
    User user = userRepository.save(User.builder()
        .name("d")
        .email("d")
        .passwordHash("a")
        .build());

    Purchase purchase = Purchase.builder()
        .user(user)
        .totalPrice(BigDecimal.valueOf(1000))
        .status(PENDING)
        .build();

    Purchase save = purchaseRepository.save(purchase);

    save.setStatus(PurchaseStatus.COMPLETED);
    purchaseRepository.save(save);
  }

  @Test
  void 삭제() {
    User user = userRepository.save(User.builder()
        .name("d")
        .email("d")
        .passwordHash("a")
        .build());

    Purchase purchase = Purchase.builder()
        .user(user)
        .totalPrice(BigDecimal.valueOf(1000))
        .status(PENDING)
        .build();
    Purchase savePurchase = purchaseRepository.save(purchase);

    purchaseRepository.delete(savePurchase);
  }

  @Test
  void 조회() {
//    Purchase purchases = purchaseRepository.findById(2L)
//        .orElseThrow(() -> new IllegalArgumentException("주문내역이 없음!"));
//
//    System.out.println("결과 : " + purchases.getId());
//    System.out.println("가격 : " + purchases.getTotalPrice());

    List<Purchase> purchases = purchaseRepository.findAll();
    for (Purchase purchase : purchases) {
      purchase.getUser().getName();
    }

  }
}