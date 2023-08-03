package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.Weather;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class WeatherResponseDto {

    private final LocalDate date;
    private final String user;

    public WeatherResponseDto(Weather weather) {
        date = weather.getDate();
        user = weather.getUser().getEmail();
    }
}
