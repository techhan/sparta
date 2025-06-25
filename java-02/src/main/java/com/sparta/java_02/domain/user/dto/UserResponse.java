package com.sparta.java_02.domain.user.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

  private String id;

  private String name;

  private String email;

  private LocalDateTime createdDate;
}
