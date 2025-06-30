package com.sparta.java_02.domain.user.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

  Long id;

  String name;

  String email;

  LocalDateTime createdAt;

  @QueryProjection
  public UserResponse(
          Long id,
          String name,
          String email,
          LocalDateTime createdAt
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.createdAt = createdAt;
  }
}