package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.dto.UserResponseDto;
import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.dto.SignupRequestDto;
import com.umc.yourweather.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(RequestURI.commonURI + "/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<User> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseDto.success(userService.signup(signupRequestDto));
    }

    @GetMapping("/mypage")
    public ResponseDto<UserResponseDto> mypage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("마이 페이지 조회 완료", userService.mypage(userDetails));
    }
}

