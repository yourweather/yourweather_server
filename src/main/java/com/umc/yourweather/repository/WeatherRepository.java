package com.umc.yourweather.repository;

import com.umc.yourweather.domain.User;
import com.umc.yourweather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    void writeWeather(Weather weather) throws Exception;

}
