package com.sparta.java_02.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceException extends RuntimeException {

  String code;
  String message;

  public ServiceException(ServiceExceptionCode code) {
    super(code.getMessage());
    this.code = code.name();
    this.message = code.getMessage();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
