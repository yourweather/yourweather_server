package com.umc.yourweather.repository.impl;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.repository.WeatherRepository;
import com.umc.yourweather.repository.jpa.WeatherJpaRepository;
import com.umc.yourweather.response.WeatherItemResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class WeatherRepositoryImpl implements WeatherRepository {

    private final WeatherJpaRepository weatherJpaRepository;

    @Override
    public Optional<Weather> findById(Long id) {
        return weatherJpaRepository.findById(id);
    }

    @Override
    public List<Weather> findWeatherByDateBetween(LocalDate startDate, LocalDate endDate) {
        return weatherJpaRepository.findWeatherByDateBetween(startDate, endDate);
    }

    @Override
    public Optional<Weather> findByDateAndUser(LocalDate localDate, User user) {
        return weatherJpaRepository.findByDateAndUser(localDate, user);
    }

    @Override
    public Optional<Weather> findByDate(LocalDate localDate) {
        return weatherJpaRepository.findByDate(localDate);
    }

    @Override
    public List<WeatherItemResponseDto> findByMonthAndUser(User user, LocalDate startDate, LocalDate endDate) {
        return weatherJpaRepository.findByMonthAndUser(user, startDate, endDate);
    }

    @Override
    public Weather save(Weather weather) { return weatherJpaRepository.save(weather); }

    @Override
    public void delete(Weather weather) { weatherJpaRepository.delete(weather); }

    @Override
    public List<Weather> findWeatherByDateBetweenAndUser(LocalDate oneWeekAgo, LocalDate current, User user) {
        return weatherJpaRepository.findWeatherByDateBetweenAndUser(oneWeekAgo, current, user);
    }
}