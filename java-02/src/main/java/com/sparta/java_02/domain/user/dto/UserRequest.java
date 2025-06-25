package com.sparta.java_02.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequest {

  @NotNull
  private String name;

  @Email
  private String email;

  @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
  private String password;
}
