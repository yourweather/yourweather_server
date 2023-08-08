package com.umc.yourweather.request;

import com.umc.yourweather.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherUpdateRequestDto {

    private Status status; //날씨 상태 enums
    private int temperature;
}