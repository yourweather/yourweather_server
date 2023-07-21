package com.umc.yourweather.repository;

import com.umc.yourweather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
