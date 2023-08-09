package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.Weather;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Getter
public class WeatherMonthlyResponseDto {
    private List<WeatherItemResponseDto> weatherList;
}