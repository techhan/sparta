package com.sparta.java_02.domain.user.service;

import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.domain.user.dto.UserSearchResponse;
import com.sparta.java_02.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<UserSearchResponse> searchAll(Long userId) {
    if (ObjectUtils.isEmpty(userId)) {
      throw new ServiceException(ServiceExceptionCode.NOT_FOUND_DATA);
    }
    return new ArrayList<>();
  }

  public void save() {

  }


}
