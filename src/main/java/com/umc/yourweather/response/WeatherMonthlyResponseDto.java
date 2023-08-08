package com.umc.yourweather.response;

import com.umc.yourweather.domain.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public class WeatherMonthlyResponseDto {

    private Status status;
    private int temperature;
    private LocalDate date;

    @Builder
    public WeatherMonthlyResponseDto(Status status, int temperature, LocalDate date) {
        this.date = date;
        this.status = status;
        this.temperature = temperature;
    }
}
