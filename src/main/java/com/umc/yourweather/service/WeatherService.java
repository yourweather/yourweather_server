package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.NoInputRequestDto;
import com.umc.yourweather.dto.NoInputResponseDto;
import com.umc.yourweather.dto.WeatherRequestDto;
import com.umc.yourweather.repository.WeatherRepository;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Transactional
    public String create(WeatherRequestDto weatherRequestDto, CustomUserDetails userDetails) {
        Weather weather = Weather.builder()
            .user(userDetails.getUser())
            .year(weatherRequestDto.getYear())
            .month(weatherRequestDto.getMonth())
            .day(weatherRequestDto.getDay())
            .build();

        weatherRepository.save(weather);

        return "날씨 생성 완료";
    }

    public NoInputResponseDto getNoInputs(@Valid NoInputRequestDto noInputRequestDto,
        CustomUserDetails userDetails) {

        // 응답 변수 추가
        NoInputResponseDto noInputResponseDto = new NoInputResponseDto();

        // 현재의 날짜 GET
        LocalDate currentDate = noInputRequestDto.getTime();

        // 1주 전의 날짜 GET
        LocalDate oneWeekAgo = currentDate.minusWeeks(1);

        List<LocalDate> dateList = new ArrayList<>();
        LocalDate dateIterator = oneWeekAgo;

        while (!dateIterator.isAfter(currentDate)) {
            dateList.add(dateIterator);
            dateIterator.plusDays(1);
        }

        for (LocalDate date : dateList) {
            if (!weatherRepository.findByYearAndMonthAndDay(date.getYear(), date.getMonthValue(),
                date.getDayOfMonth()).isPresent()) {
                noInputResponseDto.addDate(date);
            }
        }
        return noInputResponseDto;
    }
}
