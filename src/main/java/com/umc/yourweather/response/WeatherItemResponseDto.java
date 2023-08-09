package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.domain.enums.Status;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Getter
public class WeatherItemResponseDto {

    private Long id; //웨더 아이디

    private LocalDate date;
    private Status lastStatus;
    private int lastTemperature;

    public WeatherItemResponseDto(Weather weather) {
        this.id = weather.getId();
        this.date = weather.getDate();
        this.lastStatus = weather.getLastStatus();
        this.lastTemperature = weather.getLastTemperature();
    }
}
