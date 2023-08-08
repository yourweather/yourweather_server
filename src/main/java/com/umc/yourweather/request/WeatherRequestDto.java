package com.umc.yourweather.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherRequestDto {

    String localDate;

    @Builder
    public WeatherRequestDto(String localDate) {
        this.localDate = localDate;
    }
}
