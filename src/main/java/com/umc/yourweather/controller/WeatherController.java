package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.dto.WeatherRequestDto;
import com.umc.yourweather.service.WeatherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(RequestURI.WEATHER_URI)
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping("/create")
    public ResponseDto<Weather> create(@RequestBody @Valid WeatherRequestDto weatherRequestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success(weatherService.create(weatherRequestDto, userDetails));
    }
}

