package com.umc.yourweather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Role;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.exception.WeatherNotFoundException;
import com.umc.yourweather.repository.WeatherRepository;
import com.umc.yourweather.response.MissedInputResponseDto;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    WeatherService weatherService;

    @Autowired
    WeatherRepository weatherRepository;

    @Test
    @DisplayName("메모가 없을 때의 Home 조회")
    void home() {
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

        // then
        // if there is no weather object
        Assertions.assertThrows(WeatherNotFoundException.class,
            () -> weatherService.home(userDetails));
    }

    @Test
    @DisplayName("Weather 삭제")
    public void findWeather() {
        // given
        User user = User.builder()
            .email("test@test.com")
            .password("password")
            .nickname("nickname")
            .platform("platform")
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build();

        Weather weather = Weather.builder()
            .date(LocalDate.of(2023, 7, 23))
            .build();

        weatherRepository.save(weather);

        // when
        weatherService.delete(LocalDate.of(2023, 7, 23), new CustomUserDetails(user));

        // then
        assertEquals(0L, weatherRepository.count());
    }

    @Test
    @DisplayName("미입력 날짜 조회")
    void missedInputs() {
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
        MissedInputResponseDto response = weatherService.getMissedInputs(userDetails);

        // then
        assertEquals(7, response.getLocalDates().size());
    }
}