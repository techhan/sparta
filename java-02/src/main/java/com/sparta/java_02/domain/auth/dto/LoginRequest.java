package com.sparta.java_02.domain.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor // 이걸 꼭 붙여줘야한다
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    String email;

    String password;
}
