package com.sparta.java_02.domain.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateStatusRequest {

    private String name;

    private String email;

    private String password;
}
