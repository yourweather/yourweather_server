package com.umc.yourweather.controller;

import com.umc.yourweather.domain.User;
import com.umc.yourweather.dto.LoginRequestDto;
import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.dto.SignupRequestDto;
import com.umc.yourweather.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users/")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<User> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseDto.success(userService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseDto<User> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseDto.success();
    }
}

