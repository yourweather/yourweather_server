package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.dto.WeatherRequestDto;
import com.umc.yourweather.service.WeatherService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

//    @GetMapping("/find/byId/{id}")
//    public ResponseDto<Weather> getDiaryEntryById(@PathVariable Long id) {
//        return weatherService.getDiaryEntryById(id).orElse(null);
//    }ㄹ

    @GetMapping("/find/byDate")
    public List<Weather> getWeathersByDate(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return weatherService.getWeathersByDate(localDate);
    }

    @GetMapping("/find/byMonth")
    public List<Weather> getWeathersByMonth(@RequestParam("month") int month, @RequestParam("year") int year) {
        return weatherService.getWeathersByMonth(month, year);
    }
}
