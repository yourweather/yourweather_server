package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.dto.SignupRequestDto;
import com.umc.yourweather.dto.UserResponseDto;
import com.umc.yourweather.service.UserService;
import com.umc.yourweather.service.WeatherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequiredArgsConstructor
@Slf4j
@RequestMapping(RequestURI.commonURI + "/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/write")
    public Weather createWeather(@RequestBody Weather weather) {
        return weatherService.saveWeather(weather);
    }
}

