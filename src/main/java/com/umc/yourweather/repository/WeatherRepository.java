package com.umc.yourweather.repository;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.umc.yourweather.response.WeatherItemResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeatherRepository {

    Optional<Weather> findById(Long id);

    List<Weather> findWeatherByDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Weather> findByDateAndUser(LocalDate localDate, User user);

    Optional<Weather> findByDate(LocalDate localDate);

    List<WeatherItemResponseDto> findByMonthAndUser(User user, LocalDate startDate, LocalDate endDate);

    Weather save(Weather weather);

    void delete(Weather weather);
}
