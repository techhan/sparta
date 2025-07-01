package com.sparta.java_02.domain.purchase.service;

import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.domain.product.entity.Product;
import com.sparta.java_02.domain.product.repository.ProductRepository;
import com.sparta.java_02.domain.purchase.dto.PurchaseProductRequest;
import com.sparta.java_02.domain.purchase.entity.Purchase;
import com.sparta.java_02.domain.purchase.entity.PurchaseProduct;
import com.sparta.java_02.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.java_02.domain.purchase.repository.PurchaseRepository;
import com.sparta.java_02.domain.user.entity.User;
import com.sparta.java_02.domain.user.repository.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseProcessService {

  private final PurchaseRepository purchaseRepository;
  private final ProductRepository productRepository;
  private final PurchaseProductRepository purchaseProductRepository;
  private final UserRepository userRepository;

  @Transactional
  public void process(User user, List<PurchaseProductRequest> purchaseItems) {
    Purchase purchase = savePurchase(user);
    List<PurchaseProduct> purchaseProducts = createPurchaseProducts(purchaseItems,
        purchase);

    BigDecimal totalPrice = calculateTotalPrice(purchaseProducts);
    purchase.setTotalPrice(totalPrice);
  }

  private Purchase savePurchase(User user) {
    return purchaseRepository.save(Purchase.builder()
        .user(user)
        .build());
  }

  private List<PurchaseProduct> createPurchaseProducts(List<PurchaseProductRequest> itemRequest,
      Purchase purchase) {
    List<PurchaseProduct> purchaseProducts = new ArrayList<>();

    for (PurchaseProductRequest productRequest : itemRequest) {
      Product product = productRepository.findById(productRequest.getProductId())
          .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_DATA));

      if (productRequest.getQuantity() > product.getStock()) {
        throw new ServiceException(ServiceExceptionCode.OUT_OF_STOCK_PRODUCT);
      }

      product.reduceStock(productRequest.getQuantity());

      PurchaseProduct purchaseProduct = PurchaseProduct.builder()
          .product(product)
          .purchase(purchase)
          .quantity(productRequest.getQuantity())
          .price(product.getPrice())
          .build();

      purchaseProducts.add(purchaseProduct);
    }
    purchaseProductRepository.saveAll(purchaseProducts);
    return purchaseProducts;
  }

  private void validateStock(Product product, int requestQuantity) {
    if (requestQuantity > product.getStock()) {
      throw new ServiceException(ServiceExceptionCode.OUT_OF_STOCK_PRODUCT);
    }
  }

  private BigDecimal calculateTotalPrice(List<PurchaseProduct> purchaseProducts) {
    return purchaseProducts.stream()
        .map(purchaseProduct -> purchaseProduct.getPrice()
            .multiply(BigDecimal.valueOf(purchaseProduct.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
