package com.umc.yourweather.repository;

import com.umc.yourweather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
//    void create(Weather entry);
//    void delete(Long id);
//    Optional<Weather> findById(Long id);
    List<Weather> findByDate(LocalDate date);
    List<Weather> findByMonth(int month, int year);
}
