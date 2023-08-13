package com.umc.yourweather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.request.MemoRequestDto;
import com.umc.yourweather.response.MemoResponseDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemoServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    MemoService memoService;
}