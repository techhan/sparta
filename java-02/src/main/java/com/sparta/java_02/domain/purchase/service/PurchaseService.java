package com.sparta.java_02.domain.purchase.service;

import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.domain.purchase.dto.PurchaseCancelRequest;
import com.sparta.java_02.domain.purchase.dto.PurchaseCancelResponse;
import com.sparta.java_02.domain.purchase.dto.PurchaseRequest;
import com.sparta.java_02.domain.user.entity.User;
import com.sparta.java_02.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

  private final PurchaseCancelService cancelService;
  private final PurchaseProcessService purchaseProcessService;
  private final UserRepository userRepository;

  @Transactional
  public void createPurchase(PurchaseRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_USER));

    purchaseProcessService.process(user, request.getPurchaseProducts());
  }

  @Transactional
  public PurchaseCancelResponse cancelPurchase(PurchaseCancelRequest request) {
    return cancelService.cancel(request.getPurchaseId(), request.getUserId());
  }
}
