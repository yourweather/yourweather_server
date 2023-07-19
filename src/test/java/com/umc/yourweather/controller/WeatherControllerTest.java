package com.umc.yourweather.controller;

import com.umc.yourweather.dto.WeatherRequestDto;
import com.umc.yourweather.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherControllerTest {

    @Test
    @DisplayName("yyyymmdd 형태의 날짜를 분류")
    void createWeather() {
        // given
        WeatherRequestDto weatherRequestDto = new WeatherRequestDto("20230719");

//        // when
        int year = weatherRequestDto.getYear();
        int month = weatherRequestDto.getMonth();
        int day = weatherRequestDto.getDay();

//        // then
        Assertions.assertEquals(year, 2023);
        Assertions.assertEquals(month, 07);
        Assertions.assertEquals(day, 19);
    }

}
