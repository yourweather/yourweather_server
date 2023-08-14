package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.request.ChangeNicknameRequestDto;
import com.umc.yourweather.request.ChangePasswordRequestDto;
import com.umc.yourweather.response.AuthorizationResponseDto;
import com.umc.yourweather.response.ChangePasswordResponseDto;
import com.umc.yourweather.response.UserResponseDto;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.request.SignupRequestDto;
import com.umc.yourweather.response.VerifyEmailResponseDto;
import com.umc.yourweather.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(RequestURI.USER_URI)
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "회원 가입을 합니다. 반환받은 응답 헤더에 토큰이 있습니다.")
    public ResponseDto<AuthorizationResponseDto> signup(
            @RequestBody @Valid SignupRequestDto signupRequestDto,
            @RequestHeader(defaultValue = "dummy") String secretKey) {
        AuthorizationResponseDto response = userService.signup(signupRequestDto, secretKey);

        return ResponseDto.success("회원 가입 성공", response);
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이 페이지", description = "마이 페이지를 조회하는 API 입니다.")
    public ResponseDto<UserResponseDto> mypage(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("마이 페이지 조회 완료", userService.mypage(userDetails));
    }


    @PatchMapping("/nickname")
    @Operation(summary = "닉네임 변경", description = "닉네임 변경 api입니다.")
    public ResponseDto<UserResponseDto> nickname(
            @RequestBody @Valid ChangeNicknameRequestDto changeNicknameRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("닉네임 변경 완료", userService.changeNickname(
                changeNicknameRequestDto.getNickname(), userDetails.getUser().getEmail()));
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API 입니다. 기존 비밀번호와 새 비밀번호를 요청 값으로 받습니다.")
    public ResponseDto<ChangePasswordResponseDto> password(
        @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ChangePasswordResponseDto changePasswordResponseDto = userService.changePassword(
                changePasswordRequestDto, userDetails);

        return changePasswordResponseDto.isSuccess()
                ? ResponseDto.success("비밀번호 변경 성공", changePasswordResponseDto)
                : ResponseDto.fail(HttpStatus.BAD_REQUEST, "비밀번호 변경 실패", changePasswordResponseDto);
    }

    @PutMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API 입니다. 회원 상태를 비활성화 합니다.")
    public ResponseDto<UserResponseDto> withdraw(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("회원 탈퇴 성공", userService.withdraw(userDetails));
    }

    @GetMapping("/verify-user-email")
    @Operation(summary = "이메일 검사 api", description = "비밀번호 찾기 전, 유저의 이메일(아이디)가 실제 DB에 있는 이메일과 같은지 확인을 해줍니다.")
    public ResponseDto<VerifyEmailResponseDto> verifyEmail(
            @RequestParam String email) {
        VerifyEmailResponseDto responseDto = userService.verifyEmail(email);

        boolean isOauth = responseDto.isOauth();

        return isOauth
                ? ResponseDto.fail(HttpStatus.BAD_REQUEST,"Email 검증 실패: 소셜 로그인 유저입니다.", responseDto)
                : ResponseDto.success("Email 검증 성공: 존재하는 Email 입니다.", responseDto);
    }
}

