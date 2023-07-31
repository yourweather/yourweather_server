package com.umc.yourweather.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.domain.User;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.request.SignupRequestDto;
import org.junit.jupiter.api.Assertions;
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
        userService.signup(request);

        // then
        User user = userRepository.findByEmail("test@test.com")
            .orElseThrow(() -> new RuntimeException("Error"));
        assertEquals(user.getEmail(), request.getEmail());
    }
}