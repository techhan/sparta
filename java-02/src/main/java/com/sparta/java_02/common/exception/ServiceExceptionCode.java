package com.sparta.java_02.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ServiceExceptionCode {

  NOT_FOUND_DATA("데이터를 찾을 수 없습니다."),
  NOT_FOUND_USER("유저를 찾을 수 없습니다."),
  OUT_OF_STOCK_PRODUCT("상품 재고가 없습니다.");

  final String message;
}
