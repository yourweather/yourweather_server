package com.umc.yourweather.response;

import com.umc.yourweather.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
