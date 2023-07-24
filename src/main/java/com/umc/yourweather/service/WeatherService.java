package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.WeatherRequestDto;
import com.umc.yourweather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Transactional
    public String create(WeatherRequestDto weatherRequestDto, CustomUserDetails userDetails) {
        Weather weather = Weather.builder()
            .user(userDetails.getUser())
            .year(weatherRequestDto.getYear())
            .month(weatherRequestDto.getMonth())
            .day(weatherRequestDto.getDay())
            .build();

        weatherRepository.save(weather);

        return "날씨 생성 완료";
    }
    public String getWeathersByDate(WeatherResponse weatherResponse, LocalDate date) {
        weatherRepository.findByDate(date);
        return "일별 날씨 조회 완료";
    }

    public String getWeathersByMonth(WeatherResponse weatherResponse, int month, int year) {
        weatherRepository.findByMonth(month, year);

        return "일별 날씨 조회 완료";
    }
}
