package com.sparta.java_02.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchIUserDto {

    Long id;

    String name;

    String email;

    String password_hash;
    LocalDateTime created_at;

    LocalDateTime updatedAt;
}
