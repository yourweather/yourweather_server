package com.umc.yourweather.repository;

import com.umc.yourweather.domain.Weather;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findWeatherByDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Weather> findByDate(LocalDate localDate);

//    @Query("select p from Posts p order by p.id desc")
//    List<Weather> findAllDesc();
}
