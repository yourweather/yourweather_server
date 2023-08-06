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

    @Test
    @DisplayName("Memo 저장 확인")
    void write() {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 6, 12, 0, 0);
        MemoRequestDto memoRequestDto = new MemoRequestDto(Status.SUNNY, "오늘 날씨는 맑음",
            localDateTime.toString(), 99);

        CustomUserDetails userDetails = new CustomUserDetails(User.builder().build());
        userRepository.save(userDetails.getUser());

        // when
        MemoResponseDto response = memoService.write(memoRequestDto, userDetails);

        // then
        Assertions.assertEquals(LocalDateTime.parse(response.getLocalDateTime()), localDateTime);
    }
}