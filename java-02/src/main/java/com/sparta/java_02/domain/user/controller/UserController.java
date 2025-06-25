package com.sparta.java_02.domain.user.controller;


import com.sparta.java_02.common.response.ApiResponse;
import com.sparta.java_02.domain.user.dto.UserRequest;
import com.sparta.java_02.domain.user.dto.UserSearchResponse;
import com.sparta.java_02.domain.user.dto.UserUpdateStatusRequest;
import com.sparta.java_02.domain.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}")
  public ApiResponse<List<UserSearchResponse>> findAll(@PathVariable Long userId) {
    return ApiResponse.success(userService.searchAll(userId));
  }

  @PostMapping
  public void save(@Valid @RequestBody UserRequest request) {
    userService.save();
  }

  @PutMapping("/{userId}")
  public void update(@PathVariable Long userId, @RequestBody UserRequest request) {

  }

  @PatchMapping("/{userId}")
  public void updateStatus(@PathVariable Long userId,
      @RequestBody UserUpdateStatusRequest request) {

  }

  @DeleteMapping("/{userId}")
  public void delete(@PathVariable Long userId) {

  }
}
