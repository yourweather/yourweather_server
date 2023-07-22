package com.umc.yourweather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.repository.WeatherRepository;
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
    @DisplayName("WeatherRepository 조회")
    public void findWeather() {
        // given
        Weather weather = Weather.builder()
            .year(2023)
            .month(7)
            .day(23)
            .build();

        weatherRepository.save(weather);

        // when
        Weather findWeather = weatherRepository.findByYearAndMonthAndDay(2023, 7, 23)
            .orElseThrow(() -> new RuntimeException("Not Found Weather"));
        // then
        Assertions.assertEquals(findWeather.getYear(), 2023);
        Assertions.assertEquals(findWeather.getMonth(), 7);
        Assertions.assertEquals(findWeather.getDay(), 23);
    }
}