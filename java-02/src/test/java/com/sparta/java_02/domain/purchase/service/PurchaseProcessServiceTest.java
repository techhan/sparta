package com.sparta.java_02.domain.purchase.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.java_02.common.enums.PurchaseStatus;
import com.sparta.java_02.domain.product.entity.Product;
import com.sparta.java_02.domain.product.repository.ProductRepository;
import com.sparta.java_02.domain.purchase.dto.PurchaseProductRequest;
import com.sparta.java_02.domain.purchase.entity.Purchase;
import com.sparta.java_02.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.java_02.domain.purchase.repository.PurchaseRepository;
import com.sparta.java_02.domain.user.entity.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

//@SpringBootTest // <- spring bean 사용 가능하게 해줌 Mockito를 사용할 때는 없어야됨
@ExtendWith(MockitoExtension.class)
class PurchaseProcessServiceTest {

  @InjectMocks // <- bean이 아닌 mock object 주입
  private PurchaseProcessService purchaseProcessService; // 테스트 대상

  @Mock
  private PurchaseRepository purchaseRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private PurchaseProductRepository purchaseProductRepository;

  private User testUser;
  private Purchase testPurchase;
  private Product testProduct;

  @BeforeEach
  void setUp() {
    testUser = User.builder()
        .name("테스트 사용자")
        .email("test@test.com")
        .passwordHash("passwordHash")
        .build();
    ReflectionTestUtils.setField(testUser, "id", 1L);

    testProduct = Product.builder()
        .name("노트북")
        .price(new BigDecimal("1000000"))
        .stock(10)
        .build();

    ReflectionTestUtils.setField(testProduct, "id", 1L);

    testPurchase = Purchase.builder()
        .user(testUser)
        .totalPrice(BigDecimal.ZERO)
        .status(PurchaseStatus.PENDING)
        .build();

    ReflectionTestUtils.setField(testPurchase, "id", 1L);
  }

  @Test
  @DisplayName("존재하지 않는 상품을 구매하려고 하면 NoSuchElementException이 발생한다")
  void process_should_throwNoSuchElementException_when_nonExistentProduct() {
    // Given
    PurchaseProductRequest purchaseItem = new PurchaseProductRequest();
    ReflectionTestUtils.setField(purchaseItem, "productId", 999L); // 존재하지 않는 상품 ID
    ReflectionTestUtils.setField(purchaseItem, "quantity", 1);

    List<PurchaseProductRequest> purchaseItems = List.of(purchaseItem);

    when(productRepository.findById(999L)).thenReturn(Optional.empty());
    when(purchaseRepository.save(any(Purchase.class))).thenReturn(testPurchase);

    // When & Then
    assertThrows(NoSuchElementException.class, () -> {
      purchaseProcessService.process(testUser, purchaseItems);
    });

    // 검증: 상품을 찾으려고 시도했는지 확인
    verify(productRepository).findById(999L);

    // 검증: Purchase는 생성되었지만, PurchaseProduct는 생성되지 않았는지 확인
    verify(purchaseRepository).save(any(Purchase.class));
    verify(purchaseProductRepository, never()).saveAll(anyList());
  }
}