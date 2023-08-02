package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.request.ChangePasswordRequestDto;
import com.umc.yourweather.response.UserResponseDto;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.request.SignupRequestDto;
import com.umc.yourweather.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "회원 가입 api", description = "회원 가입을 합니다. 반환받은 응답 헤더에 토큰이 있습니다.")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        User user = userService.signup(signupRequestDto);
        HttpHeaders tokenHeader = userService.getTokenHeaders(user);

        return ResponseEntity.ok()
                .headers(tokenHeader)
                .body(ResponseDto.success("회원 가입 성공"));
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이 페이지 api", description = "마이 페이지를 조회하는 API 입니다.")
    public ResponseDto<UserResponseDto> mypage(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("마이 페이지 조회 완료", userService.mypage(userDetails));
    }

    @PostMapping("/password")
    @Operation(summary = "비밀번호 변경 api", description = "비밀번호 변경 API 입니다. 요청으로 보낸 데이터 값을 비밀번호로 재설정합니다.")
    public ResponseDto<UserResponseDto> password(
        @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success(userService.changePassword(changePasswordRequestDto, userDetails));
    }

    @PutMapping("/withdraw")
    @Operation(summary = "회원 탈퇴 api", description = "회원 탈퇴 API 입니다. 회원 상태를 비활성화 합니다.")
    public ResponseDto<UserResponseDto> withdraw(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("회원 탈퇴 성공", userService.withdraw(userDetails));
    }
}

