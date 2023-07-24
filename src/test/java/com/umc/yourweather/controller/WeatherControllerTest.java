package com.umc.yourweather.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.request.WeatherRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherControllerTest {

    @Test
    @DisplayName("yyyyMMdd을 LocalDate으로 파싱")
    void createWeather() {
        // given
        WeatherRequestDto weatherRequestDto = new WeatherRequestDto("20230719");

        // when
        int year = weatherRequestDto.getYear();
        int month = weatherRequestDto.getMonth();
        int day = weatherRequestDto.getDay();

        // then
        assertEquals(year, 2023);
        assertEquals(month, 7);
        assertEquals(day, 19);
    }

}
