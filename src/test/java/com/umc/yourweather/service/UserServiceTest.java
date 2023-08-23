package com.umc.yourweather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.enums.Platform;
import com.umc.yourweather.domain.enums.Role;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.repository.test.UserTestRepository;
import com.umc.yourweather.request.ChangePasswordRequestDto;
import com.umc.yourweather.request.SignupRequestDto;
import com.umc.yourweather.response.AuthorizationResponseDto;
import com.umc.yourweather.response.ChangePasswordResponseDto;
import com.umc.yourweather.response.UserResponseDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    // 의존성 주입 최소화
    private final String secretKey = "secretKey";
    private UserRepository userRepository = new UserTestRepository();
    private JwtTokenManager jwtTokenManager = new JwtTokenManager();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserService userService = new UserService(userRepository,
        passwordEncoder, jwtTokenManager);


    @BeforeEach
    void setup() {
        userRepository.save(User.builder()
            .email("test@test.com")
            .password(passwordEncoder.encode("password"))
            .nickname("nickname")
            .platform(Platform.YOURWEATHER)
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build());
    }

    @Test
    void 회원_가입을_할_수_있다() {

    }

    @Test
    void 중복_이메일은_회원_가입을_할_수_없다() {
        // given
        SignupRequestDto request = SignupRequestDto.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform(Platform.YOURWEATHER)
            .build();

        // when
        // then
        assertThrows(RuntimeException.class, () -> {
            userService.signup(request, secretKey);
        });
    }

    @Test
    @DisplayName("mypage 조회")
    void mypage() {
        // given
        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform(Platform.YOURWEATHER)
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        // when
        UserResponseDto response = userService.mypage(userDetails);

        // then
        assertEquals(userDetails.getUser().getEmail(), response.getEmail());
        assertEquals(userDetails.getUser().getNickname(), response.getNickname());
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword() {
        // given
        ChangePasswordRequestDto request = new ChangePasswordRequestDto("password2");

        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform(Platform.YOURWEATHER)
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        // when
        userService.changePassword(request, userDetails);

        // then
        User findUser = userRepository.findByEmail("test@test.com").get();
        assertEquals(findUser.getPassword(), "password2");
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdraw() {
        //given
        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform(Platform.YOURWEATHER)
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        // when
        userService.withdraw(userDetails);

        // then
        User findUser = userRepository.findByEmail("test@test.com").get();
        assertEquals(findUser.isActivate(), false);
    }

    @Test
    void 비밀번호_변경이_가능하다() {
        // given
        User user = User.builder()
            .email("test@test.com")
            .password(passwordEncoder.encode("password"))
            .nickname("nickname")
            .platform(Platform.YOURWEATHER)
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        ChangePasswordRequestDto request = ChangePasswordRequestDto.builder()
            .password("password")
            .newPassword("newPassword")
            .build();

        // when
        ChangePasswordResponseDto changePasswordResponseDto = userService.changePassword(request,
            userDetails);

        // then
        assertEquals(true, changePasswordResponseDto.isSuccess());
        assertEquals("비밀번호 변경 완료", changePasswordResponseDto.getMessage());
    }

    @Test
    void 기존_비밀번호를_틀릴_시_비밀번호_변경이_불가하다() {
        // given
        User user = User.builder()
            .email("test@test.com")
            .password(passwordEncoder.encode("password"))
            .nickname("nickname")
            .platform(Platform.YOURWEATHER)
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        ChangePasswordRequestDto request = ChangePasswordRequestDto.builder()
            .password("wrongPassword")
            .newPassword("newPassword")
            .build();

        // when
        ChangePasswordResponseDto changePasswordResponseDto = userService.changePassword(request,
            userDetails);

        // then
        assertEquals(changePasswordResponseDto.isSuccess(), false);
        assertEquals(changePasswordResponseDto.getMessage(),
            "요청으로 들어온 기존 비밀번호가 DB에 있는 정보와 일치하지 않습니다.");
    }
}
