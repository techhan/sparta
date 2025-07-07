package com.sparta.java_02.domain.auth.controller;

import com.sparta.java_02.common.exception.ServiceException;
import com.sparta.java_02.common.exception.ServiceExceptionCode;
import com.sparta.java_02.common.response.ApiResponse;
import com.sparta.java_02.domain.auth.dto.LoginRequest;
import com.sparta.java_02.domain.auth.dto.LoginResponse;
import com.sparta.java_02.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController //세션은 통신단의 역할임
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * HttpSession는 HttpServletRequest안에서 세션 관련된 기능만 모아서 만들어놓은 객체이다.
     */

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login( HttpSession session,
            @Valid @RequestBody LoginRequest request) {

        LoginResponse loginResponse = authService.login(request);

        session.setAttribute("userId", loginResponse.getUserId()); // setAttribute()하면 바로 세션 만들어짐.. (정상 응답 시)
        session.setAttribute("name", loginResponse.getName());
        session.setAttribute("email", loginResponse.getEmail());

        log.info("session id : {}", session.getId()); // 실제 클라이언트 쪽 ID

        return ApiResponse.success(authService.login(request));
    }

//    @PostMapping("/login")
//    public ApiResponse<Void> loginQueryString(LoginRequest request1, LoginRequest request2, Pageable pageable) {
//        // localhost:8080/api/auth?email=abc@naver.com&name=1234&email=abc@naver.com&name=1234
//        return ApiResponse.success();
//    }

    @GetMapping("/status")
    public ApiResponse<LoginResponse> getStatus(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String name = (String) session.getAttribute("name");
        String email = (String) session.getAttribute("email");

//        if(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(name) && ObjectUtils.isEmpty(email)) {
//            throw new ServiceException(ServiceExceptionCode.NOT_FOUND_DATA);
//        }

        return ApiResponse.success(authService.getLoginResponse(userId ,name, email));
    }

    @GetMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success();
    }
}
