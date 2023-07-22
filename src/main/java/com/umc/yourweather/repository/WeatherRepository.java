package com.umc.yourweather.repository;

import com.umc.yourweather.domain.Weather;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findAllByYearAndMonth(int year, int month);

    Optional<Weather> findByYearAndMonthAndDay(int year, int month, int day);
}
