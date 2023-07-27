package com.umc.yourweather.service;

import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.repository.WeatherRepository;
import java.time.LocalDate;
import java.util.NoSuchElementException;
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
            .date(LocalDate.of(2023, 7, 23))
            .build();

        weatherRepository.save(weather);

        // when
        Weather findWeather = weatherRepository.findByDate(LocalDate.of(2023, 7, 23))
            .orElseThrow(() -> new NoSuchElementException("Not Found Weather"));

        LocalDate localDate = findWeather.getDate();
        // then
        Assertions.assertEquals(localDate.getYear(), 2023);
        Assertions.assertEquals(localDate.getMonthValue(), 7);
        Assertions.assertEquals(localDate.getDayOfMonth(), 23);
    }
}