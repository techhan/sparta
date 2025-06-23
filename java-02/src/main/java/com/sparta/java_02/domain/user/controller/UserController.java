package com.sparta.java_02.domain.user.controller;


 import com.sparta.java_02.domain.user.dto.UserRequest;
import com.sparta.java_02.domain.user.dto.UserSearchResponse;
import com.sparta.java_02.domain.user.dto.UserUpdateStatusRequest;
import com.sparta.java_02.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserSearchResponse> findAll(@PathVariable Long userId) {
        return ResponseEntity.status(200).body(UserSearchResponse.builder().build());
    }

    @PostMapping
    public void save(@RequestBody UserRequest request) {
        userService.save();
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable Long userId, @RequestBody UserRequest request) {

    }

    @PatchMapping("/{userId}")
    public void updateStatus(@PathVariable Long userId, @RequestBody UserUpdateStatusRequest request) {

    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {

    }
}
