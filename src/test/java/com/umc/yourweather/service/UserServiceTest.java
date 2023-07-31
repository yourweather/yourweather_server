package com.umc.yourweather.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Role;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.request.ChangePasswordRequestDto;
import com.umc.yourweather.request.SignupRequestDto;
import com.umc.yourweather.response.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.save(User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform("platform")
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build());
    }

    @Test
    @DisplayName("User 저장")
    void save() {
        // given
        SignupRequestDto request = SignupRequestDto.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform("platform")
            .build();

        // when
        User user = userService.signup(request);

        // then
        assertEquals(user.getEmail(), request.getEmail());
    }

    @Test
    @DisplayName("mypage 조회")
    void mypage() {
        // given
        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform("platform")
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
    void changePassword(){
        // given
        ChangePasswordRequestDto request = new ChangePasswordRequestDto("password2");

        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform("platform")
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
    void withdraw(){
        //given
        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform("platform")
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