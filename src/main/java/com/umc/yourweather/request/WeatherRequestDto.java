package com.umc.yourweather.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WeatherRequestDto {

    int year;
    int month;
    int day;

    @Builder
    public WeatherRequestDto(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
