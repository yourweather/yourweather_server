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
import com.umc.yourweather.response.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceTest {

    // 의존성 주입 최소화
    private UserRepository userRepository = new UserTestRepository();
    private UserService userService = new UserService(userRepository,
        new BCryptPasswordEncoder(), new JwtTokenManager());

    private final String secretKey = "secretKey";

    @BeforeEach
    void setup() {
        userRepository.save(User.builder()
            .email("test@test.com")
            .password("password")
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
}
