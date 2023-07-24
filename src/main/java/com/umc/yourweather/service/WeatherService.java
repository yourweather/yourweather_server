package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.WeatherRequestDto;
import com.umc.yourweather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
    public List<Weather> getWeathersByDate(LocalDate date) {
        return weatherRepository.findByDate(date);
    }

    public List<Weather> getWeathersByMonth(int month, int year) {
        return weatherRepository.findByMonth(month, year);
    }
}
