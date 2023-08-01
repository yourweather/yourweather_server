package com.umc.yourweather.repository;

import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findWeatherByDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Weather> findByDateAndUser(LocalDate localDate, User user);
    Optional<Weather> findByDate(LocalDate localDate);
}
