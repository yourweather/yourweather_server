package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.response.HomeResponseDto;
import com.umc.yourweather.response.MissedInputResponseDto;
import com.umc.yourweather.response.ResponseDto;
//import com.umc.yourweather.request.WeatherRequestDto;
import com.umc.yourweather.response.WeatherResponseDto;
import com.umc.yourweather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
//import jakarta.validation.Valid;
import java.time.LocalDate;
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

    @GetMapping("/home")
    @Operation(summary = "홈 조회 api", description = "홈 화면 데이터를 조회하는 API 입니다.")
    public ResponseDto<HomeResponseDto> home(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("홈 데이터 조회 성공", weatherService.home(userDetails));
    }

//    @PostMapping("/create")
//    public ResponseDto<Weather> create(@RequestBody @Valid WeatherRequestDto weatherRequestDto,
//        @AuthenticationPrincipal CustomUserDetails userDetails) {
//        return ResponseDto.success(weatherService.create(weatherRequestDto, userDetails));
//    }

    @GetMapping("/no-inputs")
    @Operation(summary = "미 입력 조회 api", description = "최근 일주일 간 미 입력 날짜를 조회하는 API 입니다.")
    public ResponseDto<MissedInputResponseDto> getMissedInputs(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("미 입력 날짜 조회 성공",
            weatherService.getMissedInputs(userDetails));
    }

    @DeleteMapping("/{year}-{month}-{day}")
    @Operation(summary = "Weather 삭제 api", description = "Weather 삭제 API 입니다. 전달 받은 날짜에 해당하는 Weather 객체를 삭제합니다.")
    public ResponseDto<WeatherResponseDto> delete(@PathVariable int year,
        @PathVariable int month,
        @PathVariable int day,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return ResponseDto.success("날씨 삭제 성공", weatherService.delete(localDate, userDetails));
    }
}


