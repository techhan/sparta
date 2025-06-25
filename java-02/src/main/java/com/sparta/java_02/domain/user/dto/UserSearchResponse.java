package com.sparta.java_02.domain.user.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchResponse {

  private Long id;

  private String name;

  private String email;

  //private LocalDateTime
}
