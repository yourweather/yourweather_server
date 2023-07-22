package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.dto.ChangePasswordDto;
import com.umc.yourweather.dto.UserResponseDto;
import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.dto.SignupRequestDto;
import com.umc.yourweather.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(RequestURI.USER_URI)
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        User user = userService.signup(signupRequestDto);
        HttpHeaders tokenHeader = userService.getTokenHeaders(user);

        return ResponseEntity.ok()
                .headers(tokenHeader)
                .body(ResponseDto.success("회원 가입 성공"));
    }

    @GetMapping("/mypage")
    public ResponseDto<UserResponseDto> mypage(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("마이 페이지 조회 완료", userService.mypage(userDetails));
    }

    @PostMapping("/password")
    public ResponseDto<UserResponseDto> password(
        @RequestBody @Valid ChangePasswordDto changePasswordDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success(userService.changePassword(changePasswordDto, userDetails));
    }

    @PutMapping("/withdraw")
    public ResponseDto<UserResponseDto> withdraw(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("회원 탈퇴 성공", userService.withdraw(userDetails));
    }
}

